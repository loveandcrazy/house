import ipfsapi
import ipfshttpclient
import sys

IP='39.108.93.29'

#上传文件
def add(path):
    
    #创建连接
    client = ipfshttpclient.connect('/ip4/'+IP+'/tcp/5001/http')
    #添加文件，其中path为文件路径
    res = client.add(path)
    print(res['Hash'])
    #res形式：{'Hash': 'QmWxS5aNTFEc9XbMX1ASvLET1zrqEaTssqt33rVZQCQb22', 'Name': '1.txt'}
    return res

def cat(hash):
	# 连接ipfs服务器。
    api = ipfsapi.Client(IP, 5001)
    # 控制台打印文件
    res=api.cat(hash)
    return res


#下载文件
def get(hash):
    # 连接ipfs服务器
    api = ipfsapi.Client(IP, 5001)
    # 下载文件，下载完成后会在本地以hash为文件名
    api.get(hash)
    


#判断参数并调用对应方法
def judge(method,value):
    if method =="add":
        res=add(value)
    elif method=="get":
        res=get(value)
    elif method=="cat":
        res=cat(value)
    else:
        res="method is wrong"
    return res



if __name__ == '__main__':

  #res=judge("get","QmeHSpyg39JPJkqCLoAJPAVGJ9gGZ8Bjo4W8Msaa47iPKR")
  #主函数调用判断函数
  res=judge(sys.argv[1], sys.argv[2])
  #print(res['Hash'])


