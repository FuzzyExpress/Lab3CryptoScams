# I used this to get the categories and subcategories from the urls.csv file.
# I expected it would yeild a reasonable amount of subcategories, but it was over 500.
# I think it was a string input so people just put what they want.

import csv

main = []
sub  = []

file = open('urls.csv', 'r')
out = open('Catagories.out', 'w')

writer = csv.writer(out)
reader = csv.reader(file)
for line in reader:
    if line[2] not in main:
        main.append(line[2])
    if line[3] not in sub:
        sub.append(line[3])

for m in main:
    writer.writerow([m])

writer.writerow('')
writer.writerow('')
writer.writerow('')

for s in sub:
    writer.writerow([s])

file.close()
out.close()

