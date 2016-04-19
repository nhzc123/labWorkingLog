import math

if __name__ == "__main__":
    sessionFile = open("/home/media/zc/logData/0418/informationGain","r")
    lines = sessionFile.readlines()
    sessionFile.close()
    outFile = open("/home/media/zc/logData/0418/igResult","w")

    entropy = {}
    totalLine = 0
    sessionLen = 12
    entropy[sessionLen -1] = {}

    for line in lines:
        l = line.strip().split("\t")
        totalLine += 1
        if l[sessionLen - 1] not in entropy[sessionLen - 1]:
            entropy[sessionLen - 1][l[sessionLen - 1]] = 1
        else:
            entropy[sessionLen - 1][l[sessionLen - 1]] += 1


        for i in range(0,sessionLen - 1):
            if i not in entropy:
                entropy[i] = {}

            if l[i] not in entropy[i]:
                entropy[i][l[i]] = 1
            else:
                entropy[i][l[i]] += 1

            keyId = l[i] + "$" + l[sessionLen - 1]

            if keyId not in entropy[i]:
                entropy[i][keyId] = 1
            else:
                entropy[i][keyId] += 1

    entropyS = 0
    for a, b in entropy[sessionLen - 1].items():
        num = (float(b) / totalLine)
        entropyS -= (num * math.log(num,2)) 

    for i in range(0, sessionLen - 1):
        print("haha" + "$$$$$$$$$$" + str(i))
        result = {}
        for a, b in entropy[i].items():
            key = a.split("$")
            if (len(key) < 2):
                continue
            print(b)
            print(entropy[i][key[0]])
            print("########")
            num = (float(b) / entropy[i][key[0]])
            if key[0] not in result:
                result[key[0]] = 0
            result[key[0]] -= (num * math.log(num,2))

        temEntropy = entropyS
        for a, b in result.items():
            temEntropy -= b * (float(entropy[i][a]) / totalLine)

        outFile.write(str(i) + "\t" + str(float("%0.12f"%temEntropy)) + "\n")








