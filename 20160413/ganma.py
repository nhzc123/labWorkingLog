##
# @file ganma.py
# @brief 
# @author nhzc   charles.nhzc@gmail.com
# @version 1.0.0
# @date 2016-04-11

import math
r = 186
k = 0.3
x = 1 + 1 / k
result = math.sqrt(2 * math.pi) * math.exp(-x)*(x**(x - 0.5))

ex = result * r
print(ex)
