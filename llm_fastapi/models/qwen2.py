import torch
from config import Config
from transformers import AutoModelForCausalLM, AutoTokenizer, Qwen2ForCausalLM, Qwen2Tokenizer
from typing import List, Dict


device = Config.device
model: Qwen2ForCausalLM = AutoModelForCausalLM.from_pretrained(
    Config.llm_model_path,
    torch_dtype="auto",
    device_map=device
)
tokenizer: Qwen2Tokenizer = AutoTokenizer.from_pretrained(Config.llm_model_path)

@torch.inference_mode()
def stream_chat(messages: List[Dict[str, str]]):
    max_new_tokens = 512
    text = tokenizer.apply_chat_template(
        messages,
        tokenize=False,
        add_generation_prompt=True
    )
    model_inputs = tokenizer([text], return_tensors="pt").to(device)
    ids: torch.Tensor = model_inputs.input_ids
    for i in range(max_new_tokens):
        output = model(ids)
        output_token = int(torch.argmax(output.logits[0, -1, :]))
        if output_token == tokenizer.eos_token_id:
            yield "", 1
        ids = torch.concat([ids, torch.tensor([[output_token]], dtype=torch.int).to(device)], dim=-1)
        yield tokenizer.decode(output_token), 0
    yield "", 1

@torch.inference_mode()
def chat(messages: List[Dict[str, str]]) -> str:
    text = tokenizer.apply_chat_template(
        messages,
        tokenize=False,
        add_generation_prompt=True
    )
    model_inputs = tokenizer([text], return_tensors="pt").to(device)
    generated_ids = model.generate(
        model_inputs.input_ids,
        max_new_tokens=512
    )
    generated_ids = [
        output_ids[len(input_ids):] for input_ids, output_ids in zip(model_inputs.input_ids, generated_ids)
    ]
    response = tokenizer.batch_decode(generated_ids, skip_special_tokens=True)[0]
    return response
