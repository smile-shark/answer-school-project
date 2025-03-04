"""
1.通过账号密码获取到token
2.通过获取到的token向服务器发起大量的time请求
"""

import requests
import time
from threading import Thread
from concurrent.futures import ThreadPoolExecutor,ProcessPoolExecutor#ThreadPoolExecutor是线程池,ProcessPoolExecutor是进程

def Totime():
    session = requests.session()
    User_List={
        'Name':'500106200501259212',
        'Password':'030707'
        }
    tokenUser={
                'username': User_List['Name'],                    #账号
                'password': User_List['Password'],                #密码
                'code': '2341',
                'vid': '',
                'client_id': '43215cdff2d5407f8af074d2d7e589ee',
                'client_secret': 'DBqEL1YfBmKgT9O491J1YnYoq84lYtB/LwMabAS2JEqa8I+r3z1VrDqymjisqJn3',
                'grant_type': 'password',
                'tenant_id': '32'
            }
    resptaken=session.post('https://ai.cqzuxia.com/connect/token',data=tokenUser)
    token=resptaken.json()

    #设置验证的头部
    answer_headers = {
                'Accept':
                'application/json, text/plain, */*',
                'Accept-Encoding':
                'gzip, deflate, br, zstd',
                'Accept-Language':
                'zh-CN,zh-TW;q=0.9,zh;q=0.8,en-US;q=0.7,en;q=0.6',
                'Authorization':token['token_type']+' '+token['access_token'],
                'Cookie':'Hm_lvt_6a39124bf36b1d4977b320040aa48563=1721104292',
                'Priority':
                'u=1, i',
                'Referer':
                'https://ai.cqzuxia.com/',
                'Sec-Ch-Ua':
                '"Not/A)Brand";v="8", "Chromium";v="126", "Google Chrome";v="126"',
                'Sec-Ch-Ua-Mobile':
                '?0',
                'Sec-Ch-Ua-Platform':
                '"Windows"',
                'Sec-Fetch-Dest':
                'empty',
                'Sec-Fetch-Mode':
                'cors',
                'Sec-Fetch-Site':
                'same-origin',
                'User-Agent':
                'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36'
        }

    #时间请求url
    time_url='https://ai.cqzuxia.com/education/api/Student/SaveOnlineLogs'
    #请求数据
    time_data={
        "ssname":""
    }
    time_data2={
        "ssname":"evaluation"
    }
    time_data3={
        "ssname":"3"
    }

    
    #获取当前存储时间：
    responses=session.get('https://ai.cqzuxia.com/education/api/Student/GetOnlineInfo?',headers=answer_headers)
    print('总时间:'+str(responses.json()['data']['totalTimes']),end=" ")
    print('今日时间:'+str(responses.json()['data']['todayTimes']))



    #发起请求
    response=session.post(url=time_url,headers=answer_headers,data=time_data)
    response2=session.post(url=time_url,headers=answer_headers,data=time_data2)
    response3=session.post(url=time_url,headers=answer_headers,data=time_data3)
    print(response3.text)
    response.close()
    response2.close()
    response3.close()
    resptaken.close()
    responses.close()
    time.sleep(60)

def func():
    while True:
        Totime()

if __name__ == '__main__':
    with ThreadPoolExecutor(1) as t:
        for i in range(1):
            t.submit(func)
print('程序结束')







