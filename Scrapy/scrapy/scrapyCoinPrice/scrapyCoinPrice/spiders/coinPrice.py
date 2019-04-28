import asyncio
import logging
import xlwt
import numpy as np
from aiowebsocket.converses import AioWebSocket


async def startup(uri):
    async with AioWebSocket(uri) as aws:
        converseBTC = aws.manipulator
        converseETH = aws.manipulator
        converseXRP = aws.manipulator
        converseBTH = aws.manipulator
        converseEOS = aws.manipulator
        converseLTC = aws.manipulator
        converseBNB = aws.manipulator
        converseUSDT = aws.manipulator
        converseXMR = aws.manipulator
        converseDASH = aws.manipulator
        # 客户端给服务端发送消息
        await converseBTC.send('{"event":"subscribe","type":"price","channel":"bitcoin"}')
        await converseETH.send('{"event":"subscribe","type":"price","channel":"ethereum"}')
        await converseXRP.send('{"event":"subscribe","type":"price","channel":"ripple"}')
        await converseBTH.send('{"event":"subscribe","type":"price","channel":"bitcoin-cash"}')
        await converseEOS.send('{"event":"subscribe","type":"price","channel":"eos"}')
        await converseLTC.send('{"event":"subscribe","type":"price","channel":"litecoin"}')
        await converseBNB.send('{"event":"subscribe","type":"price","channel":"binance-coin"}')
        await converseUSDT.send('{"event":"subscribe","type":"price","channel":"usd-coin"}')
        await converseXMR.send('{"event":"subscribe","type":"price","channel":"monero"}')
        await converseDASH.send('{"event":"subscribe","type":"price","channel":"dash"}')
        while True:
            BTCMsg = await converseBTC.receive()
            ETHMsg = await converseETH.receive()
            XRPMsg = await converseXRP.receive()
            BTHMsg = await converseBTH.receive()
            EOSMsg = await converseEOS.receive()
            LTCMsg = await converseLTC.receive()
            BNBMsg = await converseBNB.receive()
            USDTMsg = await converseUSDT.receive()
            XMRMsg = await converseXMR.receive()
            DASHMsg = await converseDASH.receive()

            wb = xlwt.Workbook()
            ws = wb.add_sheet('coin', cell_overwrite_ok=True)

            ws.write(0, 0, '币种')
            ws.write(0, 1, '价格')
            ws.write(0, 2, '24H涨跌幅')
            ws.write(0, 3, '24H成交量')
            ws.write(0, 4, '')
            ws.write(0, 5, '')

            BTCData = bytes.decode(BTCMsg, 'utf-8').replace('[', '').replace(']', '').replace('"', '').split(',')
            BTCData = np.array(BTCData)

            ETHData = bytes.decode(ETHMsg, 'utf-8').replace('[', '').replace(']', '').replace('"', '').split(',')
            ETHData = np.array(ETHData)

            XRPData = bytes.decode(XRPMsg, 'utf-8').replace('[', '').replace(']', '').replace('"', '').split(',')
            XRPData = np.array(XRPData)

            BTHData = bytes.decode(BTHMsg, 'utf-8').replace('[', '').replace(']', '').replace('"', '').split(',')
            BTHData = np.array(BTHData)

            EOSData = bytes.decode(EOSMsg, 'utf-8').replace('[', '').replace(']', '').replace('"', '').split(',')
            EOSData = np.array(EOSData)

            LTCData = bytes.decode(LTCMsg, 'utf-8').replace('[', '').replace(']', '').replace('"', '').split(',')
            LTCData = np.array(LTCData)

            BNBData = bytes.decode(BNBMsg, 'utf-8').replace('[', '').replace(']', '').replace('"', '').split(',')
            BNBData = np.array(BNBData)

            USDTData = bytes.decode(USDTMsg, 'utf-8').replace('[', '').replace(']', '').replace('"', '').split(',')
            USDTData = np.array(USDTData)

            XMRData = bytes.decode(XMRMsg, 'utf-8').replace('[', '').replace(']', '').replace('"', '').split(',')
            XMRData = np.array(XMRData)

            DASHData = bytes.decode(DASHMsg, 'utf-8').replace('[', '').replace(']', '').replace('"', '').split(',')
            DASHData = np.array(DASHData)

            for btc in BTCData:
                print(btc)
                ws.write(1, 0, BTCData[0])
                ws.write(1, 1, BTCData[1])
                ws.write(1, 2, BTCData[2])
                ws.write(1, 3, BTCData[3])
                ws.write(1, 4, BTCData[4])
                ws.write(1, 5, BTCData[5])

            for eth in ETHData:
                print(eth)
                ws.write(2, 0, ETHData[0])
                ws.write(2, 1, ETHData[1])
                ws.write(2, 2, ETHData[2])
                ws.write(2, 3, ETHData[3])
                ws.write(2, 4, ETHData[4])
                ws.write(2, 5, ETHData[5])

            for xrp in XRPData:
                print(xrp)
                ws.write(3, 0, XRPData[0])
                ws.write(3, 1, XRPData[1])
                ws.write(3, 2, XRPData[2])
                ws.write(3, 3, XRPData[3])
                ws.write(3, 4, XRPData[4])
                ws.write(3, 5, XRPData[5])

            for bth in BTHData:
                print(bth)
                ws.write(4, 0, BTHData[0])
                ws.write(4, 1, BTHData[1])
                ws.write(4, 2, BTHData[2])
                ws.write(4, 3, BTHData[3])
                ws.write(4, 4, BTHData[4])
                ws.write(4, 5, BTHData[5])

            for eos in EOSData:
                print(eos)
                ws.write(5, 0, EOSData[0])
                ws.write(5, 1, EOSData[1])
                ws.write(5, 2, EOSData[2])
                ws.write(5, 3, EOSData[3])
                ws.write(5, 4, EOSData[4])
                ws.write(5, 5, EOSData[5])

            for ltc in LTCData:
                print(ltc)
                ws.write(6, 0, LTCData[0])
                ws.write(6, 1, LTCData[1])
                ws.write(6, 2, LTCData[2])
                ws.write(6, 3, LTCData[3])
                ws.write(6, 4, LTCData[4])
                ws.write(6, 5, LTCData[5])

            for bnb in BNBData:
                print(bnb)
                ws.write(7, 0, BNBData[0])
                ws.write(7, 1, BNBData[1])
                ws.write(7, 2, BNBData[2])
                ws.write(7, 3, BNBData[3])
                ws.write(7, 4, BNBData[4])
                ws.write(7, 5, BNBData[5])

            for usdt in USDTData:
                print(usdt)
                ws.write(8, 0, USDTData[0])
                ws.write(8, 1, USDTData[1])
                ws.write(8, 2, USDTData[2])
                ws.write(8, 3, USDTData[3])
                ws.write(8, 4, USDTData[4])
                ws.write(8, 5, USDTData[5])

            for xmr in XMRData:
                print(xmr)
                ws.write(9, 0, XMRData[0])
                ws.write(9, 1, XMRData[1])
                ws.write(9, 2, XMRData[2])
                ws.write(9, 3, XMRData[3])
                ws.write(9, 4, XMRData[4])
                ws.write(9, 5, XMRData[5])

            for dash in DASHData:
                print(dash)
                ws.write(10, 0, DASHData[0])
                ws.write(10, 1, DASHData[1])
                ws.write(10, 2, DASHData[2])
                ws.write(10, 3, DASHData[3])
                ws.write(10, 4, DASHData[4])
                ws.write(10, 5, DASHData[5])

            wb.save('./coin.xls')

if __name__ == '__main__':
    remote = 'wss://ws.niuyan.com/api/v2/web/coin/ws'
    try:
        asyncio.get_event_loop().run_until_complete(startup(remote))
    except KeyboardInterrupt as exc:
        logging.info('Quit.')