#-*- encoding:utf-8 -*-

if __name__ == "__main__":
    sessionFile = open("/mnt/1t/backup/2013Data/info/session_video", "r")
    lines = sessionFile.readlines()
    sessionFile.close()
    videoType = {}
    videoCount = {}
    outFile = open("/home/media/zc/logData/0411/videoLenCal", "w")

    for line in lines:
        l = line.strip().split("\t")
        try:
            vLen = float(l[4])
        except Exception, e:
            print(line.strip())
            continue

        if vLen > 20000:
            continue

        if l[3] in videoType:
            videoType[l[3]] += vLen
            videoCount[l[3]] += 1
        else:
            videoType[l[3]] = vLen
            videoCount[l[3]] = 1

    for k, v in videoType.items():
        outFile.write(k + "\t" + str(videoType[k] / videoCount[k]) + "\n")

    outFile.close()



