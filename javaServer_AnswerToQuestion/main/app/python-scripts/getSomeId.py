import requests
import json
import sys
import result.PythonResult as PythonResult
import result.URL as URL
import getToken
import os
curPath = os.path.abspath(os.path.dirname(__file__))
rootPath = os.path.split(curPath)[0]
sys.path.append(rootPath)

username = sys.argv[1],
password = sys.argv[2]

session = requests.Session()


def get_SubID(courseId):
    # 尝试通过课程id获取到章节id
    idParam = {
        'CourseID': courseId
    }
    course = {
        "courseId": courseId,
        "chapters": []
    }

    reqSubsection = session.get(URL.subsection_url, params=idParam, headers=URL.getHeaders(token))
    # 循环每个章节
    value = reqSubsection.json()
    course["courseName"] = value['data']['courseName']
    try:
        for chapterRequest in value['data']['chapters']:
            # 循环每个小节（有可能会出现有章节没有小节的情况）
            chapter = {
                "chapterId": chapterRequest['id'],
                "chapterTitle": chapterRequest['title'],
                "chapterName": chapterRequest['titleContent'],
                "courseId": courseId,
                "subsections": []
            }
            try:
                for sub in chapterRequest['knowledgeList']:
                    subsection = {
                        'subsectionId': sub['id'],
                        'subsectionName': sub['knowledge'],
                        'courseId': courseId,
                        'chapterId': chapterRequest['id'],
                    }
                    chapter['subsections'].append(subsection)
            except:
                pass
            course["chapters"].append(chapter)
    except:
        pass
    reqSubsection.close()
    return course

token = getToken.getToken(username, password)

# 尝试获取课程id
reqAllCourse = session.get(URL.course_url, headers=URL.getHeaders(token))

courseIds = []
for reqCourseId in reqAllCourse.json()['data']['unfinished']:
    courseIds.append(reqCourseId['courseID'])
# completeList是已经完成了的,unfinished是未完成的
for reqCourseId in reqAllCourse.json()['data']['completeList']:
    courseIds.append(reqCourseId['courseID'])
# print(courseId)   获取到了课程id存放在courseId的列表中
# 这里修改为返回给java程序，通过java去存储
PythonResult.result["isGetCourseId"] = True

for courseId in courseIds:
    PythonResult.result['courses'].append(get_SubID(courseId))

print(json.dumps(PythonResult.result))

reqAllCourse.close()
