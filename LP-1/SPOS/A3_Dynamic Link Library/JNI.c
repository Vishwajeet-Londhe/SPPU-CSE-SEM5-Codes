#include <JNI.h>
#include<math.h>

#define PI 3.14159265

JNIEXPORT void JNICALL Java_JNI_JNI_JniAdd
  (JNIEnv *e, jobject obj, jint no1, jint no2)
{
int add=no1+no2;
printf("Addition of nos.= %d",add);
}

JNIEXPORT void JNICALL Java_JNI_JNI_JniSub
  (JNIEnv *e, jobject obj, jint no1, jint no2)
{
  int sub=no1-no2;
  printf("\nSubtraction of nos. is= %d",sub);
}

JNIEXPORT void JNICALL Java_JNI_JNI_JniMult
  (JNIEnv *e, jobject obj, jint no1, jint no2)
{
   int mult=no1*no2;
  printf("\nMultiplication of nos. is= %d",mult);
}


JNIEXPORT void JNICALL Java_JNI_JNI_JniDiv
  (JNIEnv *e, jobject obj, jdouble no1, jdouble no2)
{
  double div=no1/no2;
  printf("\nDivision of nos. is= %.3f",div);
}

JNIEXPORT void JNICALL Java_JNI_JNI_JniMod
  (JNIEnv *e, jobject obj, jint no1, jint no2)
{
   printf("\nRemainder is= %.3f",fmod(no1,no2));
}

JNIEXPORT void JNICALL Java_JNI_JNI_JniPow
  (JNIEnv *e, jobject obj, jint no1, jint no2)
{
   printf("\nPower is= %.3f",pow(no1,no2));
}


JNIEXPORT void JNICALL Java_JNI_JNI_JniSqrt
  (JNIEnv *e, jobject obj, jint no1)
{
  printf("\nSquare root %d is= %.3f",no1,sqrt(no1));

}

