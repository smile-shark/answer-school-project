import json

result = {
    "isLogin": False,
    "isGetCourseId": False,
    "user":{},
    "courses": [],
    "questionCount":0,
    "subsectionTdList":[],
    "finishContent":"",
    "finishCount":0
}


def getResultFalseLogin():
    print(json.dumps(result))
def getResultFalseGetCourseId():
    print(json.dumps(result))

def getResultTrueLogin(userId, userPassword, userName):
    result["user"]["userId"] = userId
    result["user"]["userPassword"] = userPassword
    result["user"]["userName"] = userName
    result["isLogin"] = True
    print(json.dumps(result))
