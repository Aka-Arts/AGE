package org.akaarts.earlyalpha.AGE.utils;

public class UVMap4 {
	
	public float[] U = new float[4];
	public float[] V = new float[4];
	
	public UVMap4(){
		
	}
	
	public UVMap4(float u1, float v1, float u2, float v2, float u3, float v3, float u4, float v4){
		this.U[0] = u1;
		this.U[1] = u2;
		this.U[2] = u3;
		this.U[3] = u4;
		this.V[0] = v1;
		this.V[1] = v2;
		this.V[2] = v3;
		this.V[3] = v4;	
	}
	
	public UVMap4(String UVexpr1, String UVexpr2, String UVexpr3, String UVexpr4){
		
	}
	
//	/**
//	 * float array constructor for arrays with 8 elements (u1,v1,u2,v2,u3,...)
//	 * @param UV - a float array with preferably 8 indexes
//	 */
//	public UVMap4(float[] UV){
//		for(int i = 0;i<8;i+=2){
//			
//			try{
//				this.U[i/2] = UV[i];
//			}catch(ArrayIndexOutOfBoundsException){
//				this.U[i/2] =
//			}
//			
//			
//		}
//	}

}
