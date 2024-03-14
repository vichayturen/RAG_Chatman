package com.vichayturen.rag_chatman.document;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Data
@Slf4j
public class TextSpliter {
    private int chunkSize;
    private int chunkOverlap;
    private List<String> separators;
    private boolean stripWhitespace;

    public TextSpliter(int chunkSize, int chunkOverlap, List<String> separators, boolean stripWhitespace) {
        if (chunkOverlap > chunkSize) {
            throw new RuntimeException("chunkSize should be smaller than chunkOverlap!");
        }
        this.chunkSize = chunkSize;
        this.chunkOverlap = chunkOverlap;
        this.separators = separators;
        this.stripWhitespace = stripWhitespace;
    }

    public List<String> splitText(String text, List<String> separators) {
        List<String> finalChunks = new ArrayList<>();
        String separator = separators.get(separators.size()-1);
        List<String> newSeparators = new ArrayList<>();
        for (int i = 0; i < separators.size(); i++) {
            String _s = separators.get(i);
            if (_s.isEmpty()) {
                separator = _s;
                break;
            }
            if (text.matches(_s)) {
                separator = _s;
                newSeparators = separators.subList(i+1, separators.size());
                break;
            }
        }
        String _separator = separator;
        List<String> splits = splitTextWithRegex(text, _separator);
        List<String> goodSplits = new ArrayList<>();
        _separator = "";
        for (String s: splits) {
            if (s.length() < this.chunkSize) {
                goodSplits.add(s);
            } else {
                if (!goodSplits.isEmpty()) {
                    List<String> mergedText = mergeSplits(goodSplits, _separator);
                    finalChunks.addAll(mergedText);
                    goodSplits.clear();
                }
                if (newSeparators.isEmpty()) {
                    finalChunks.add(s);
                } else {
                    List<String> otherInfo = splitText(s, newSeparators);
                    finalChunks.addAll(otherInfo);
                }
            }
        }
        if (!goodSplits.isEmpty()) {
            List<String> mergedText = mergeSplits(goodSplits, _separator);
            finalChunks.addAll(mergedText);
        }
        return finalChunks;
    }

    private List<String> splitTextWithRegex(String text, String separator) {
        LinkedList<String> splits = new LinkedList<>();
        if (!separator.isEmpty()) {
            String[] _splits = text.split(separator);
            for (int i = 1; i < _splits.length; i+=2) {
                splits.add(_splits[i] + _splits[i+1]);
            }
            if (_splits.length % 2 == 0) {
                splits.add(_splits[_splits.length-1]);
            }
            splits.addFirst(_splits[0]);
        } else {
            String[] splitArray = text.split(separator);
            splits.addAll(Arrays.asList(splitArray));
        }
        splits.removeIf(String::isEmpty);
        return splits;
    }

    private List<String> mergeSplits(List<String> splits, String separator) {
        int separatorLen = separator.length();
        List<String> docs = new ArrayList<>();
        List<String> currentDoc = new ArrayList<>();
        int total = 0;
        for (String d: splits) {
            int _len = d.length();
            int tmp = total + _len;
            if (currentDoc.size() > 0) {
                tmp += separatorLen;
            }
            if (tmp > this.chunkSize) {
                if (total > this.chunkSize) {
                    log.warn("Created a chunk of size which is longger than this.chunkSize!");
                }
                if (currentDoc.size() > 0) {
                    String doc = joinDocs(currentDoc, separator);
                    if (doc != null) {
                        docs.add(doc);
                    }
                    tmp = total + _len;
                    if (currentDoc.size() > 0) {
                        tmp += separatorLen;
                    }
                    while (total > this.chunkOverlap || tmp > this.chunkSize && total > 0) {
                        total -= currentDoc.get(0).length();
                        if (currentDoc.size() > 1) {
                            total -= separatorLen;
                        }
                        currentDoc.remove(0);
                        tmp = total + _len;
                        if (currentDoc.size() > 0) {
                            tmp += separatorLen;
                        }
                    }
                }
            }
            currentDoc.add(d);
            total += _len;
            if (currentDoc.size() > 1) {
                total += separatorLen;
            }
        }
        String doc = joinDocs(currentDoc, separator);
        if (doc != null) {
            docs.add(doc);
        }
        return docs;
    }

    private String joinDocs(List<String> docs, String separator) {
        StringBuilder sb = new StringBuilder(docs.get(0));
        for (int i = 1; i < docs.size(); i++) {
            sb.append(separator);
            sb.append(docs.get(i));
        }
        String text = sb.toString();
        if (this.stripWhitespace) {
            text = text.strip();
        }
        if (text.isEmpty()) {
            return null;
        } else {
            return text;
        }
    }

    public static List<String> split(String text) {
        TextSpliter textSpliter = TextSpliter.builder().build();
        return textSpliter.splitText(text, textSpliter.getSeparators());
    }

    public static TextSpliter.Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int chunkSize;
        private int chunkOverlap;
        private List<String> separators;
        private boolean stripWhitespace;

        public Builder() {
            this.chunkSize = 4000;
            this.chunkOverlap = 200;
            this.separators = new ArrayList<>();
            this.separators.add("\\n\\n");
            this.separators.add("\\n");
            this.separators.add("。");
            this.separators.add("，");
            this.separators.add("");
            this.stripWhitespace = false;
        }

        public void chunkSize(int chunkSize) {this.chunkSize = chunkSize;}
        public void chunkOverlap(int chunkOverlap) {this.chunkOverlap = chunkOverlap;}
        public void separators(List<String> separators) {this.separators = separators;}
        public void stripWhitespace(boolean stripWhitespace) {this.stripWhitespace = stripWhitespace;}

        public TextSpliter build() {
            return new TextSpliter(this.chunkSize,
                    this.chunkOverlap,
                    this.separators,
                    this.stripWhitespace);
        }
    }
}
