#-*- encoding:utf-8 -*-
import time


if __name__ == "__main__":
    sessionFile = open("/mnt/1t/backup/2013Data/session/bitrate_types/summary","r")
    cdnFile = open("/mnt/1t/backup/2013Data/session/bitrate_types/serverLocation","r")
    userFile = open("/mnt/1t/backup/2013Data/session/bitrate_types/userLocation","r")
    sessionLine = sessionFile.readlines()
    cdnLine = cdnFile.readlines()
    userLine = userFile.readlines()
    cdnFile.close()
    userFile.close()
    sessionFile.close()

    outFile = open("/home/media/zc/logData/0418/informationGain","w")

    cdnDic = {}
    for line in cdnLine:
        l = line.strip().split("\t")
        cdnDic[l[1]] = []
        cdnDic[l[1]].append(l[0])
        cdnDic[l[1]].append(l[2])
        cdnDic[l[1]].append(l[3])

    userDic = {}
    for line in userLine:
        l = line.strip().split("\t")
        userDic[l[0]] = []
        userDic[l[0]].append(l[1])
        userDic[l[0]].append(l[2])

    for line in sessionLine:
        try:
            l = line.strip().split("\t")
            wLine = ""

            if (len(l) < 32):
                continue

            deviceResult = ""
            if(l[6] == "4" or l[6] == "2"):
                deviceResult = 1
            elif(l[6] == "103" or l[6] == "116" or l[6] == "11"):
                deviceResult = 2
            elif(l[6] == "1" or l[6] == "5"):
                deviceResult = 3
            else:
                print("device " + line)
                continue

            videoLen = ""
            vtem = float(l[12])
            if(vtem > 0 and vtem <= 20 * 60):
                videoLen = 1
            elif(vtem > 20 * 60 and vtem <= 60 * 60):
                videoLen = 2
            elif(vtem > 60 * 60 and vtem <= 4 * 60 * 60):
                videoLen = 3
            else:
                print("video " + line)
                continue

            nowPlayPercent = ""
            ptem = float(l[20])
            if (ptem <= 20):
                nowPlayPercent = 1
            elif(ptem > 20 and ptem <= 40):
                nowPlayPercent = 2
            elif(ptem > 40 and ptem <= 60):
                nowPlayPercent = 3
            elif(ptem > 60 and ptem <= 80):
                nowPlayPercent = 4
            elif(ptem > 80 and ptem <= 100):
                nowPlayPercent = 5
            else:
                print("playPercent " + line)
                continue

            watchingTime = ""
            wtem = float(l[21])

            wtime = float(time.strftime("%H",time.localtime(wtem)))
            if(wtime > 1 and wtime <= 6):
                watchingTime = 1
            elif(wtime > 6 and wtime <= 12):
                watchingTime = 2
            elif(wtime > 12 and wtime <= 18):
                watchingTime = 3
            else:
                watchingTime = 4

            videoType = ""
            vtem = l[11]
            if(vtem == "movie"):
                videoType = 1 
            elif(vtem == "series"):
                videoType = 2
            elif(vtem == "children"):
                videoType = 3
            elif(vtem == "documentary"):
                videoType = 5
            elif(vtem == "sports"):
                videoType = 4
            elif(vtem == "news"):
                videoType = 6
            elif(vtem == "fun"):
                videoType = 7
            elif(vtem == "others"):
                videoType = 8

            startDelay = ""
            sdtem = float(l[30])
            if (sdtem > 0 and sdtem <= 2):
                startDelay = 1
            elif(sdtem > 2 and sdtem <= 5):
                startDelay = 2
            elif(sdtem > 5 and sdtem <= 10):
                startDelay = 3
            elif(sdtem > 10 and sdtem < 60):
                startDelay = 4
            else:
                print("startDelay" + line)
                continue

            cdnName = cdnDic[l[0]][0]

            serverISP = ""
            sitem = cdnDic[l[0]][2]
            if(sitem =="联通"):
                serverISP = 1
            elif(sitem == "电信"):
                serverISP = 2
            elif(sitem == "移动"):
                serverISP = 3
            elif(sitem == "其他"):
                serverISP = 4
            else:
                print("serverISP" + line)
                continue

            userISP = ""
            if l[1] not in userDic:
                continue
            sitem = userDic[l[1]][1]
            if(sitem =="联通"):
                userISP = 1
            elif(sitem == "电信"):
                userISP = 2
            elif(sitem == "移动"):
                userISP = 3
            elif(sitem == "其他"):
                userISP = 4
            else:
                print("userISP" + line)
                continue

            ISPsame = ""
            if (userISP == serverISP):
                ISPsame = 1
            else:
                ISPsame = 2

            locationSame = ""
            if(userDic[l[1]][0] == cdnDic[l[0]][1]):
                locationSame = 1
            else:
                locationSame = 2

#            result = ""
#            if(l[15] == "no"):
#                result = 1
#            else:
#                result = 2
            result = ""
            rtem = float(l[14])
            if (rtem <=20):
                result = 1
            elif(rtem > 20 and rtem <= 40):
                result = 2
            elif(rtem > 40 and rtem <= 60):
                result = 3
            elif(rtem > 60 and rtem <= 80):
                result = 4
            else:
                result = 5



            outFile.write(str(deviceResult) + "\t" + str(videoLen) + "\t" + str(nowPlayPercent) + "\t" + str(watchingTime) + "\t" + str(videoType) + "\t" + str(startDelay) + "\t" + str(cdnName) + "\t" + str(serverISP) + "\t" + str(userISP) + "\t" + str(ISPsame) + "\t" + str(locationSame) + "\t" + str(result) + "\n")

                
            
        except Exception, e:
            print(e)


