package com.wuruidong;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import javax.imageio.ImageIO;

//22:17 2017/11/13//��ɢ��ѧ��ҵjavaʵ��
//ͼ���ģ���������㷨
/*
ʵ�־���ģ�����������磺
A[2][3]=[1 2 3
         4 5 6    
         7 8 9],   ��ת�����B����Ϊ��
B[2][3]=[1 2 1
         2 5 2
         2 3 2].
*/

//������
class Matrix{
private int A[][];
public int rows,columns;//������к���
public Matrix(){
  rows=100;columns=100;
  A=new int[rows][columns];
}
public Matrix(int m,int n){
  rows=m;columns=n;
  A=new int[rows][columns];
}
public Matrix(Matrix B){
  this.rows=B.rows;
  this.columns=B.columns;
  A=new int[this.rows][this.columns];
}
public Matrix(int length,Matrix B){
  this.rows=B.rows+2*length;
  this.columns=B.columns+2*length;
  A=new int[this.rows][this.columns];
}
public Matrix(String string) throws IOException, ClassNotFoundException{
	File file=new File(string);
	BufferedImage image=ImageIO.read(file);
	//Image image=Toolkit.getDefaultToolkit().getImage(file.getPath());
	this.rows=image.getWidth();
	this.columns=image.getHeight();
	A=new int[this.rows][this.columns];
	for(int i=0;i<this.rows;i++){
		for(int j=0;j<this.columns;j++){
			A[i][j]= image.getRGB(i, j);
		}
	}
}


//����������Ԫ��
public void add(int i,int j,int num){
  try{
     A[i][j]=num;
     }catch(Exception e){ }
}
//��ʾ������ĳ��ĳ�е�Ԫ��
public int get(int i,int j){
  return A[i][j];
}
//������չ����ԭ����Ϊ���ģ���Χ���0Ԫ��
public Matrix extendMatrix(int len){
  Matrix B=new Matrix(len,this);
  for(int i=0;i<2*len+this.rows;i++){
    for(int j=0;j<2*len+this.columns;j++){
      if(i>=len&&i<len+this.rows&&j>=len&&j<len+this.columns){
        B.add(i,j,this.get(i-len,j-len));
      }
      else{
       B.add(i,j,0);
      }
    }
  }
  return B;
}
//������ǰi��j�еĺͣ���������һ������
public Matrix subSumMatrix(){
  Matrix B=new Matrix(this);
int num=0;
  for(int i=0;i<B.rows;i++){
    for(int j=0;j<B.columns;j++){ 
      for(int m=0;m<=i;m++){
        for(int n=0;n<=j;n++){
          num+=this.get(m,n);
        }
      }
    B.add(i,j,num);
    num=0;
    }
  }
  return B;
}
//����ģ������2
public Matrix mixMatrix2(int len,int order){
  Matrix B=this.extendMatrix(len);
  Matrix C=B.subSumMatrix();
  Matrix D=new Matrix(this);
  int num=0;
  for(int i=len;i<len+this.rows;i++){
    for(int j=len;j<len+this.columns;j++){
      num=((C.get(i+order,j+order)+C.get(i-1-order,j-1-order)-
    		  C.get(i-1-order,j+order)-C.get(i+order,j-1-order))/((2*order+1)*(2*order+1)));
      D.add(i-len,j-len,num);
    }
    num=0;
  }
  return D;
}
        

//����ģ������
public Matrix mixMatrix(){
  Matrix B=new Matrix(this);
int num=0;
  for(int i=0;i<B.rows;i++){
    for(int j=0;j<B.columns;j++){
      if(i-1<0&&j-1<0){
        num+=this.get(i,j+1)+this.get(i+1,j)+this.get(i+1,j+1)+this.get(i,j);
        B.add(i,j,num/9);
      }
      else if(i+1>=B.rows&&j+1>=B.columns){
        num+=this.get(i,j-1)+this.get(i-1,j)+this.get(i-1,j-1)+this.get(i,j);
        B.add(i,j,num/9);
      }
      else if(i-1<0&&j+1>=B.columns){
        num+=this.get(i,j-1)+this.get(i+1,j)+this.get(i+1,j-1)+this.get(i,j);
        B.add(i,j,num/9);
      }
      else if(i+1>=B.rows&&j-1<0){
        num+=this.get(i,j+1)+this.get(i-1,j)+this.get(i-1,j+1)+this.get(i,j);
        B.add(i,j,num/9);
      }
      else if(i-1<0){
        num+=this.get(i,j-1)+this.get(i+1,j-1)+this.get(i+1,j)+this.get(i+1,j+1)+this.get(i,j+1)+this.get(i,j);
        B.add(i,j,num/9);
      }
      else if(i+1>=B.rows){
        num+=this.get(i,j-1)+this.get(i-1,j-1)+this.get(i-1,j)+this.get(i-1,j+1)+this.get(i,j+1)+this.get(i,j);
        B.add(i,j,num/9);
      }
      else if(j-1<0){
        num+=this.get(i-1,j)+this.get(i-1,j+1)+this.get(i,j+1)+this.get(i+1,j+1)+this.get(i+1,j)+this.get(i,j);
        B.add(i,j,num/9);
      }
      else if(j+1>=B.columns){
        num+=this.get(i-1,j)+this.get(i-1,j-1)+this.get(i,j-1)+this.get(i+1,j-1)+this.get(i+1,j)+this.get(i,j);
        B.add(i,j,num/9);
      }
      else{
        num+=this.get(i,j-1)+this.get(i,j+1)+this.get(i-1,j)+this.get(i+1,j)+
                      this.get(i-1,j-1)+this.get(i-1,j+1)+this.get(i+1,j+1)+this.get(i+1,j-1)+this.get(i,j);
        B.add(i,j,num/9);
      }
    num=0;
    }
  }
  return B;
}
//�����������
public void print(){
  for(int i=0;i<this.rows;i++){
    for(int j=0;j<this.columns;j++){
      System.out.print(this.get(i,j)+" ");
    }
    System.out.println();
  }
  System.out.println("---------------------------");
}
//ͼƬ�������
public void pringPicture(String string,String type) throws IOException{
	int width=this.rows;
	int height=this.columns;
	int[] data=new int[width*height];
	for(int i=0;i<height;i++){
		for(int j=0;j<width;j++){
			data[i*width+j]=(int) this.A[j][i];
		}
	}
	BufferedImage bf=new BufferedImage(width,height,BufferedImage.TYPE_INT_BGR);
	bf.setRGB(0, 0, width,height,data,0,width);
	File file=new File(string);
	ImageIO.write((RenderedImage)bf, type, file);
	
}
}

//������
public class Test{
public static void main(String[] args) 
		throws ClassNotFoundException, IOException{
  Matrix A=new Matrix(10,8);
  for(int i=0;i<A.rows;i++){
    for(int j=0;j<A.columns;j++){
      A.add(i,j,i*j+1);
    }
  }
  A.print();
  Matrix C=A.subSumMatrix();
  C.print();
  Matrix B=A.mixMatrix();
  B.print();
  Matrix D=A.mixMatrix2(100,20);
  D.print();
  Matrix E=new Matrix("D:/����Ϸ/eclipse/test24/src/Picturn(5).jpg");
  Matrix F=E.mixMatrix2(10,1);
  F.pringPicture("D:/����Ϸ/eclipse/test24/MixPicture4.jpg","jpg");
}
}
