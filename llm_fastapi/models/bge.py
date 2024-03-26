from typing import List

from transformers import AutoTokenizer, AutoModel
import torch
from config import Config


tokenizer = AutoTokenizer.from_pretrained(Config.embedding_model_path)
model = AutoModel.from_pretrained(Config.embedding_model_path).to(Config.device)
model.eval()

@torch.inference_mode()
def embedding(sentence: str) -> List[float]:
    encoded_input = tokenizer([sentence], padding=True, truncation=True, max_length=512, return_tensors='pt').to(Config.device)
    print(encoded_input)
    model_output = model(**encoded_input)
    sentence_embeddings = model_output[0][:, 0]
    sentence_embeddings = torch.nn.functional.normalize(sentence_embeddings, p=2, dim=1)
    return sentence_embeddings.to("cpu").numpy().tolist()[0]
