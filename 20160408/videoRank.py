#-*- encoding:utf-8 -*-

if __name__ == "__main__":
    vFile = open("/home/media/zc/logData/0408/videoRankScore", "r")
    lines = vFile.readlines()
    sumNum = float(1275881)
    totalNum = float(32475)
    outputFile = open("/home/media/zc/logData/0408/videoRankPoint","w")
    viewTime = 0
    viewRank = 0

    for line in lines:
        rank = viewRank / totalNum
        viewRank += 1
        viewPercent = sumNum / 1275881
        viewTime = float(line.strip())
        sumNum -= viewTime
        outputFile.write(str(rank) + "\t" + str(viewPercent) + "\n")


    outputFile.close()
    print(str(sumNum))
    vFile.close()

        
