#-*- encoding:utf-8 -*-

if __name__ == "__main__":
    sessionFile = open("/mnt/1t/backup/2013Data/session/sessionsWithVideo","r")
    lines = sessionFile.readlines()
    sessionFile.close()
    outFile1 = open("/home/media/zc/logData/0414/s1","w")
    outFile2 = open("/home/media/zc/logData/0414/s2","w")
    outFile3 = open("/home/media/zc/logData/0414/s3","w")
    outFile4 = open("/home/media/zc/logData/0414/s4","w")
    outFile5 = open("/home/media/zc/logData/0414/s5","w")
    outFile6 = open("/home/media/zc/logData/0414/s6","w")
    outFile7 = open("/home/media/zc/logData/0414/s7","w")

    for line in lines:
        l = line.strip().split("\t")
        s1 = float(l[16])
        s2 = float(l[17])
        s3 = float(l[18])
        s4 = float(l[19])
        s5 = float(l[20])
        s6 = float(l[21])
        s7 = float(l[22])
        maxRate = s1
        maxIndex = 1
        if (s2 > maxRate):
            maxRate = s2
            maxIndex = 2
        if (s3 > maxRate):
            maxRate = s3
            maxIndex = 3
        if (s4 > maxRate):
            maxRate = s4
            maxIndex = 4
        if (s5 > maxRate):
            maxRate = s5
            maxIndex = 5
        if (s6 > maxRate):
            maxRate = s6
            maxIndex = 6
        if (s7 > maxRate):
            maxRate = s7
            maxIndex = 7

        if (maxRate == 0):
            print(line)
            continue

        if (maxIndex == 1):
            outFile1.write(l[35] + "\n")
        if (maxIndex == 2):
            outFile2.write(l[35] + "\n")
        if (maxIndex == 3):
            outFile3.write(l[35] + "\n")
        if (maxIndex == 4):
            outFile4.write(l[35] + "\n")
        if (maxIndex == 5):
            outFile5.write(l[35] + "\n")
        if (maxIndex == 6):
            outFile6.write(l[35] + "\n")
        if (maxIndex == 7):
            outFile7.write(l[35] + "\n")

    outFile1.close()
    outFile2.close()
    outFile3.close()
    outFile4.close()
    outFile5.close()
    outFile6.close()
    outFile7.close()
