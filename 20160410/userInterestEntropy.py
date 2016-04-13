#-*- encoding:utf-8 -*-
import math

if __name__ == "__main__":
    sessionFile = open("/mnt/1t/backup/2013Data/session/sessionsWithVideo","r")
    lines = sessionFile.readlines()
    sessionFile.close()
    userInfo = {}
    outFile = open("/home/media/zc/logData/0410/userEntropy","w")

    for line in lines:
        l = line.strip().split("\t")
        userId = l[1]
        videoType = l[33]

        if (videoType == ""):
            continue

        if userId in userInfo:
            userInfo[userId][l[33]] += 1
            userInfo[userId]['sum'] += 1
        else:
            userInfo[userId] = {}
            userInfo[userId]['sum'] = 1
            userInfo[userId]['children'] = 0
            userInfo[userId]['documentary'] = 0
            userInfo[userId]['sports'] = 0
            userInfo[userId]['news'] = 0
            userInfo[userId]['series'] = 0
            userInfo[userId]['movie'] = 0
            userInfo[userId]['fun'] = 0
            userInfo[userId]['others'] = 0
            userInfo[userId][l[33]] = 1

    for key, val in userInfo.items():
        totalEntropy = 0
        ui = float(val['sum'])
        
        for k, v in userInfo[key].items():
            if k == "sum":
                continue
            if v == 0:
                continue

            p = v / ui 
            totalEntropy += (p * math.log(p, math.e))

        outFile.write(str(-totalEntropy / float(math.log(8, math.e))) + "\n")
    outFile.close()
