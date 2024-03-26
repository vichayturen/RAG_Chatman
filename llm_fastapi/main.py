import json
from fastapi import FastAPI, Query, Path
import uvicorn
from starlette.requests import Request
import asyncio
from sse_starlette import EventSourceResponse
from models.bge import embedding
from models.qwen2 import stream_chat


app = FastAPI()

@app.get("/")
async def index():
    return {"Hello": "World"}

@app.post("/chat/stream")
async def stream(request: Request):
    print(await request.json())
    async def event_generator(request: Request):
        body = await request.json()
        messages = body["messages"]
        generator = stream_chat(messages)
        idx = 0
        mark = 0
        while mark == 0:
            if await request.is_disconnected():
                # print("连接已中断")
                break
            generate_string, mark = next(generator)
            yield json.dumps({"id": str(idx), "content": generate_string}, ensure_ascii=False)
            idx += 1
        yield "[DONE]"
    return EventSourceResponse(event_generator(request))

@app.get("/embedding")
async def embed(text: str) -> dict:
    embeds = await embedding(text)
    return {
        "embedding": embeds
    }

if __name__ == "__main__":
    uvicorn.run(app="main:app", host="0.0.0.0", port=1222, reload=False)
