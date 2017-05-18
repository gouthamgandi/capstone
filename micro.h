#include <unistd.h>
#include <iostream>
#include <exception>
#include "mraa.hpp"
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
using namespace std;

//#############################################################################################################
int main()
{


	int sockfd; // socket file descriptor
	struct sockaddr_in serv_addr;
	struct hostent *server;
	char area_id[13]="XYZ123456789";
	char type[5]="Toll";
	char mode[5]="FOUR";
	mraa::Uart* dev;
	dev = new mraa::Uart(0);
	cout<<"UART SUCCESS"<<"\n";
	char array[12];
	int portnum=9000;
	char *ip="192.168.1.100";

//#############################################################################################################
   for(;;)
   {
	sockfd = socket(AF_INET, SOCK_STREAM, 0); // generate file descriptor
	if (sockfd < 0)
	    perror("ERROR opening socket");

	server = gethostbyname(ip); //the ip address (or server name) of the listening server.
	if (server == NULL) {
	    fprintf(stderr,"ERROR, no such host\n");
	    exit(0);
	}

	bzero((char *) &serv_addr, sizeof(serv_addr));
	serv_addr.sin_family = AF_INET;
	bcopy((char *)server->h_addr, (char *)&serv_addr.sin_addr.s_addr, server->h_length);
	serv_addr.sin_port = htons(portnum);


// #################################################################################################################

	   if (connect(sockfd,(struct sockaddr *)&serv_addr,sizeof(serv_addr)) < 0)
	   		   	    perror("ERROR connecting");

	   		       cout<<" Connection Success"<<endl;
	   		   	//  Connection Done !

    dev->read(array,12);

    for(int i=0;i<12;i++)
    cout<<array[i];
    cout<<"\n";

    send(sockfd,array,12,0);
    send(sockfd,"\n",1,0);
    send(sockfd,area_id,12,0);
    send(sockfd,"\n",1,0);
    send(sockfd,type,4,0);
    send(sockfd,"\n",1,0);
    send(sockfd,mode,4,0);
    send(sockfd,"\n",1,0);
    cout<<"Success"<<endl;
    close(sockfd);
   }
   //#########################################################################################################
   //never executes
   delete dev;
   return mraa::SUCCESS;
	return 0;
}
