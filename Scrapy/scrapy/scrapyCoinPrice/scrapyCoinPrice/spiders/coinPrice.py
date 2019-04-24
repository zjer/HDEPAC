import asyncio
import logging
from datetime import datetime
from aiowebsocket.converses import AioWebSocket


async def startup(uri):
    async with AioWebSocket(uri) as aws:
        converse = aws.manipulator
        # 客户端给服务端发送消息
        await converse.send('{"event":"subscribe","type":"price","channel":"bitcoin"}')
        await converse.send('{"event":"subscribe","type":"price","channel":"ethereum"}')
        await converse.send('{"event":"subscribe","type":"price","channel":"ripple"}')
        await converse.send('{"event":"subscribe","type":"price","channel":"bitcoin-cash"}')
        await converse.send('{"event":"subscribe","type":"price","channel":"eos"}')
        await converse.send('{"event":"subscribe","type":"price","channel":"litecoin"}')
        await converse.send('{"event":"subscribe","type":"price","channel":"binance-coin"}')
        await converse.send('{"event":"subscribe","type":"price","channel":"usd-coin"}')
        await converse.send('{"event":"subscribe","type":"price","channel":"monero"}')
        await converse.send('{"event":"subscribe","type":"price","channel":"dash"}')
        while True:
            mes = await converse.receive()
            print('{time}-Client receive: {rec}'
                  .format(time=datetime.now().strftime('%Y-%m-%d %H:%M:%S'), rec=mes))


if __name__ == '__main__':
    remote = 'wss://ws.niuyan.com/api/v2/web/coin/ws'
    try:
        asyncio.get_event_loop().run_until_complete(startup(remote))
    except KeyboardInterrupt as exc:
        logging.info('Quit.')