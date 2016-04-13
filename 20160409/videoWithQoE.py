#-*- encoding:utf-8 -*-

if __name__ == "__main__":
    idFile = open("/home/media/zc/logData/0408/videoRank","r")
    idLine = idFile.readlines()
    videoRate = {}
    videoLen = {}
    videoNum = {}
    outFile = open("/home/media/zc/logData/0408/videoWithQoE","w")

    sessionFile = open("/mnt/1t/backup/2013Data/session/sessionsWithVideo","r")

    for line in idLine:
        l = line.strip().split("\t")
        videoRate[l[1]] = 0
        videoLen[l[1]] = 0
        videoNum[l[1]] = 0

    sessionLine = sessionFile.readlines()

    for line in sessionLine:
        l = line.strip().split("\t")

        if l[5] in videoRate:
            tem = float(l[15])
            if (tem > 2000):
                #print(line)
                continue
            tem = float(l[35])
            if (tem > 1 ):
                #print(line)
                continue
            if (l[34] == ""):
                #print(line)
                continue
            tem = float(l[34])
            if (tem > 20000):
                #print(line)
                continue
            videoLen[l[5]] += float(l[15]) * 10
            videoRate[l[5]] += float(l[35])
            videoNum[l[5]] += 1
        else:
            a = 1
            #print(line)

    idCount = 0
    idTotal = float(32475) 
    totalRate = 0
    totalLen = 0
    totalNum = 0
    flag = 1
    for line in idLine:
        l = line.strip().split("\t")
        idCount += 1
        totalLen += videoLen[l[1]]
        totalRate += videoRate[l[1]]
        totalNum += videoNum[l[1]]

        if (videoNum[l[1]] != 0):
            print(line.strip() + "\t" + str(videoLen[l[1]] / videoNum[l[1]]) + "\t" + str(videoRate[l[1]] / videoNum[l[1]]))
        else:
            print(line.strip() + "\t0\t0")


        if (flag == 1 and (idCount / idTotal > 0.01)):
            outFile.write(str(totalLen / totalNum) + "\t" + str(totalRate / totalNum) + "\n")
            flag = 2

        if (flag == 2 and (idCount / idTotal > 0.05)):
            outFile.write(str(totalLen / totalNum) + "\t" + str(totalRate / totalNum) + "\n")
            flag = 3

        if (flag == 3 and (idCount / idTotal > 0.10)):
            outFile.write(str(totalLen / totalNum) + "\t" + str(totalRate / totalNum) + "\n")
            flag = 4

        if (flag == 4 and (idCount / idTotal > 0.20)):
            outFile.write(str(totalLen / totalNum) + "\t" + str(totalRate / totalNum) + "\n")
            flag = 5

        if (flag == 5 and (idCount / idTotal > 0.50)):
            outFile.write(str(totalLen / totalNum) + "\t" + str(totalRate / totalNum) + "\n")
            flag = 6

        if (flag == 6 and (idCount / idTotal > 0.99)):
            outFile.write(str(totalLen / totalNum) + "\t" + str(totalRate / totalNum) + "\n")
            flag = 7

    outFile.close()
    idFile.close()
    sessionFile.close()

        

        

