
#senha_list = "RESP 848920-SP"
#with open('listaIDsAcordaos.txt','a') as f:  
#    f.write('{}\n'.format(senha_list))


with open("listaIDsAcordaos.txt", "r") as tf:
    lines = tf.read().split('\n')
    
for line in lines:
    print(line)