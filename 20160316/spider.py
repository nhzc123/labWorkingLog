#-*- encoding:utf-8 -*-
# @file spider.py
# @brief 
# @author nhzc   charles.nhzc@gmail.com
# @version 1.0.0
# @date 2016-03-16
import urllib
import json
import time
import sys
reload(sys)
sys.setdefaultencoding('utf8')

def getHtml(video, typeVariable):
    time.sleep(2)
    if (typeVariable == "search"):
        url = "http://api.douban.com/v2/movie/search?apikey=0ec6ac38a8bfeb1a2c5404a9fa3fc14e&count=1&q=" + video
    else:
        url = "https://api.douban.com/v2/movie/subject/" + video + "?apikey=0ec6ac38a8bfeb1a2c5404a9fa3fc14e"
    page = urllib.urlopen(url)
    html = page.read()
    decodeStr = json.loads(html) 
    return decodeStr

if __name__ == "__main__":
    try:
        outStream = open("spiderResult", "a")
        outStream.write("num\tvideoName\tgenres\tplayers\tintroduction\n")
        outErr = open("errMessage", "w")
        f = open("../20160107/info/uniqVideoContent")
        #f = open("../20160107/info/uvc")
        lines = f.readlines()

        for line in lines:
            try:
                l = line.split("\t")
                videoName = l[1]
                jsonStr = getHtml(videoName, "search")

                if jsonStr["total"] == 0:
                    outStream.write(line.strip() + "\tnull\tnull\tnull\n")
                else:
                    outStream.write(line.strip() + "\t")

                    if "subjects" in jsonStr:

                        if len(jsonStr["subjects"]) != 0:

                            if "genres" in jsonStr["subjects"][0]:
                                for genre in jsonStr["subjects"][0]["genres"]:
                                    outStream.write(genre + "&&&")
                                outStream.write("\t")
                            else:
                                outStream.write("null\t")

                            if "casts" in jsonStr["subjects"][0]:
                                for playerName in jsonStr["subjects"][0]["casts"]:
                                    outStream.write(playerName["name"] + "&&&")
                                outStream.write("\t")
                            else:
                                outStream.write("null\t")

                            if "id" in jsonStr["subjects"][0]:
                                videoId = jsonStr["subjects"][0]["id"]
                                videoContent = getHtml(videoId, "detail")
                                if "summary" in videoContent:
                                    outStream.write(videoContent["summary"].replace("\n", "\t"))
                                else:
                                    outStream.write("null")
                            else:
                                outStream.write("null")
                            
                            outStream.write("\n")
                        else:
                            outStream.write("null\tnull\tnull\n")
                    else:
                        outStream.write("null\tnull\tnull\n")

            except Exception, e:
                errMessage.write(line)
                print(e)
    except Exception, e:
        print(e)


