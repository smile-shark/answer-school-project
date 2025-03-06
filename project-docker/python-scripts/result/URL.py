# 登录地址
login_url = 'https://ai.cqzuxia.com/connect/token'
# 所有课程
course_url = 'https://ai.cqzuxia.com/evaluation/api/stuevaluatereport/GetCourseProgram?'
# 小节
subsection_url = 'https://ai.cqzuxia.com/evaluation/api/studentevaluate/GetCourseInfoByCourseId'
# 获取到问题
question_uUrl = 'https://ai.cqzuxia.com/evaluation/api/studentevaluate/beginevaluate'
# 答题地址：
answer_url = 'https://ai.cqzuxia.com/evaluation/api/StudentEvaluate/SaveEvaluateAnswer'

def getHeaders(token):
    # 设置验证的头部
    return {
        'Accept':
            'application/json, text/plain, */*',
        'Accept-Encoding':
            'gzip, deflate, br, zstd',
        'Accept-Language':
            'zh-CN,zh-TW;q=0.9,zh;q=0.8,en-US;q=0.7,en;q=0.6',
        'Authorization': token['token_type'] + ' ' + token['access_token'],
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