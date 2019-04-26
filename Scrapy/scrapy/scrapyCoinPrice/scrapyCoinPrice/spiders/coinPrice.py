import asyncio
import logging
import xlwt
import numpy as np
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

            wb = xlwt.Workbook()
            ws = wb.add_sheet('coin', cell_overwrite_ok=True)

            ws.write(0, 0, '币种')
            ws.write(0, 1, '价格')
            ws.write(0, 2, '24H成交额')
            ws.write(0, 3, '市值')
            ws.write(0, 4, '成交量')
            ws.write(0, 5, '24H涨跌幅')

            wb.save('./coin.xls')

            data = mes.decode('utf-8').replace('\n', '')

            print(np.array(data))

            for i, item in data:
                print(i)
                print(item)
                ws.write(i + 1, 0, item)
                ws.write(i + 1, 1, item)
                ws.write(i + 1, 2, item)
                ws.write(i + 1, 3, item)
                ws.write(i + 1, 4, item)
                ws.write(i + 1, 5, item)

            wb.save('./coin.xls')

if __name__ == '__main__':
    remote = 'wss://ws.niuyan.com/api/v2/web/coin/ws'
    try:
        asyncio.get_event_loop().run_until_complete(startup(remote))
    except KeyboardInterrupt as exc:
        logging.info('Quit.')