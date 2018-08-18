# -*- coding: utf-8 -*-
import wechatsogou
import json

ws_api = wechatsogou.WechatSogouAPI()
#
# ws_api = wechatsogou.WechatSogouAPI(captcha_break_time=3)
#
# ws_api = wechatsogou.WechatSogouAPI(proxies={
#     "http": "127.0.0.1:8888",
#     "https": "127.0.0.1:8888",
# })
#
# ws_api = wechatsogou.WechatSogouAPI(timeout=0.1)

output = ws_api.get_gzh_article_by_history('Pioneer先锋教育')
strOutput = json.dumps(output)

print (strOutput)

