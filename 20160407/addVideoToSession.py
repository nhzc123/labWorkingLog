#-*- encoding:utf-8 -*-
import os

if __name__ == "__main__":
    videoFile = open("/mnt/1t/backup/2013Data/info/session_video","r")
    lines = videoFile.readlines()
    videoContent = {}

    for line in lines:
        l = line.split("\t")
        videoDetail = []
        #2 for name , 3 for type, 4 for length
        videoDetail.append(l[2])
        videoDetail.append(l[3])
        videoDetail.append(l[4])
        videoContent[l[0]] = videoDetail 

    sessionFile = open("/mnt/1t/backup/2013Data/session/sessions", "r")
    lines = sessionFile.readlines()
    outputFile = open("/mnt/1t/backup/2013Data/session/sessionsWithVideo","w")

    for line in lines:
        l = line.split("\t")
        if l[5] in videoContent:
            if videoContent[l[5]][2] != "":
                videoLength = float(videoContent[l[5]][2])
            else:
                videoLength = 0
            if videoLength == 0:
                videoRate = 0
            else:
                videoRate = float(l[15]) / videoLength
            outputFile.write(line.strip() + "\t" + videoContent[l[5]][0] + "\t" + videoContent[l[5]][1] + "\t" + videoContent[l[5]][2] + "\t" + str(videoRate) + "\n")
        else:
            print(line)

    sessionFile.close()
    outputFile.close()
    videoFile.close()


