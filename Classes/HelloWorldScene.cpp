#include "HelloWorldScene.h"
USING_NS_CC;

#include "ODSocket/ODSocket.h"

Scene* HelloWorld::createScene()
{
	auto scene = Scene::create();
	auto layer = HelloWorld::create();
	scene->addChild(layer);
	return scene;
}

bool HelloWorld::init()
{
	if (!Layer::init()) return false;

	connectServer();

	return true;
}


// Socker����
void HelloWorld::connectServer()
{
	// ��ʼ��
	// ODSocket socket;
	socket.Init();
	socket.Create(AF_INET, SOCK_STREAM, 0);

	// ���÷�������IP��ַ���˿ں�
	// �����ӷ����� Connect
	const char* ip = "127.0.0.1";
	int port = 12345;
	bool result = socket.Connect(ip, port);

	// �������� Send
	char * message = "login";
	socket.Send(message, 5);

	if (result) {
		CCLOG("connect to server success!");
		// �������̣߳������߳��У���������
		std::thread recvThread = std::thread(&HelloWorld::receiveData, this);
		recvThread.detach(); // �����̷߳���
	}
	else {
		CCLOG("can not connect to server");
		return;
	}
}

// ��������
void HelloWorld::receiveData()
{
	// ��Ϊ��ǿ����
	// ���Կ���һֱ��������Ƿ������ݴ���
	while (true) {
		// �������� Recv
		char data[512] = "";
		int result = socket.Recv(data, 512, 0);
		printf("%d", result);
		// ������������ӶϿ���
		if (result <= 0) break;

		CCLOG("%s", data);
	}
	// �ر�����
	socket.Close();
}

