import requests
import sys
import io
import os
import result.PythonResult as Result
curPath = os.path.abspath(os.path.dirname(__file__))
rootPath = os.path.split(curPath)[0]
sys.path.append(rootPath)

# 确保 Python 的标准输出使用 GBK 编码
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf8')

UNameURL = 'https://ai.cqzuxia.com/education/api/Student/GetStudentInfo?'


def main():
    try:
        if len(sys.argv) != 3:
            Result.getResultFalseLogin()
            sys.exit(1)

        username = sys.argv[1]
        password = sys.argv[2]

        tokenUser = {
            'username': username,
            'password': password,
            'code': '2341',
            'vid': '',
            'client_id': '43215cdff2d5407f8af074d2d7e589ee',
            'client_secret': 'DBqEL1YfBmKgT9O491J1YnYoq84lYtB/LwMabAS2JEqa8I+r3z1VrDqymjisqJn3',
            'grant_type': 'password',
            'tenant_id': '32'
        }

        resp = requests.post('https://ai.cqzuxia.com/connect/token', data=tokenUser)
        if resp.status_code != 200:
            Result.getResultFalseLogin()
            sys.exit(1)

        token = resp.json()
        resp.close()

        if 'token_type' not in token or 'access_token' not in token:
            Result.getResultFalseLogin()
            sys.exit(1)
        answer_headers = {
            'Accept': 'application/json, text/plain, */*',
            'Accept-Encoding': 'gzip, deflate, br, zstd',
            'Accept-Language': 'zh-CN,zh-TW;q=0.9,zh;q=0.8,en-US;q=0.7,en;q=0.6',
            'Authorization': token['token_type'] + ' ' + token['access_token'],
            'Priority': 'u=1, i',
            'Referer': 'https://ai.cqzuxia.com/',
            'Sec-Ch-Ua': '"Not/A)Brand";v="8", "Chromium";v="126", "Google Chrome";v="126"',
            'Sec-Ch-Ua-Mobile': '?0',
            'Sec-Ch-Ua-Platform': '"Windows"',
            'Sec-Fetch-Dest': 'empty',
            'Sec-Fetch-Mode': 'cors',
            'Sec-Fetch-Site': 'same-origin',
            'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36'
        }

        req2 = requests.get(UNameURL, headers=answer_headers)
        if req2.status_code != 200:
            Result.getResultFalseLogin()
            sys.exit(1)

        student_info = req2.json()
        req2.close()

        if 'data' not in student_info or 'stuInfo' not in student_info['data'] or 'studentName' not in \
                student_info['data']['stuInfo']:
            Result.getResultFalseLogin()
            sys.exit(1)

        Result.getResultTrueLogin(username, password, student_info['data']['stuInfo']['studentName'])

    except Exception as e:
        Result.getResultFalseLogin()
        sys.exit(1)


if __name__ == "__main__":
    main()
