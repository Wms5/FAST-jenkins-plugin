import re
from datetime import datetime

str = "DJe 12/01/2022"
b = re.findall(r"\d\d/\d\d/\d\d\d\d",str)

c = datetime.strptime(b[0], '%d/%m/%Y')

print(c.year)