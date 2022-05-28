import re
from datetime import datetime

str = "Ministro GURGEL DE FARIA (1160)"

b = re.findall(r"([a-zA-Z\s]+)",str)

print(b)