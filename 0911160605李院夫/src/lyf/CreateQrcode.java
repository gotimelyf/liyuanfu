package lyf;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;


import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;


public class CreateQrcode {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		int version=6;
	    
	    int imageSize=250;
		Qrcode qrcode=new Qrcode();
		qrcode.setQrcodeErrorCorrect('H');
		qrcode.setQrcodeEncodeMode('B');
		qrcode.setQrcodeVersion(version);
		String content="http://www.dijiaxueshe.com";
		byte[] data=content.getBytes("utf-8");
		boolean[][] qrdata=qrcode.calQrcode(data);
		
		BufferedImage bufferedImage=new BufferedImage(imageSize,imageSize,BufferedImage.TYPE_INT_RGB);
		Graphics2D gs=bufferedImage.createGraphics();
		gs.setBackground(Color.pink);
		
		gs.clearRect(0, 0, imageSize, imageSize);
		
		int StartR=255;	int StartG=67;	int StartB=200;
		int endR=99;int endG=0;int endB=25;
		int pixoff=2;
		for(int i=0;i<qrdata.length;i++){
			for(int j=0;j<qrdata.length;j++){
				if(qrdata[i][j]){
					/*Random rand=new Random();
					int a=rand.nextInt(233);
					int b=rand.nextInt(233);
					int c=rand.nextInt(233);*/
					int a=StartR+(endR-StartR)*(i+1)/qrdata.length;
					int b=StartG+(endG-StartG)*(i+1)/qrdata.length;
					int c=StartB+(endB-StartB)*(i+1)/qrdata.length;
					Color color=new Color(a, b, c);
					gs.setColor(color);
					gs.fillRect(i*6+pixoff, j*6+pixoff, 6, 6);
					
				}
			}
	}
		BufferedImage logo=scale("D:/logo.jpg",60,60,true);
		
		//int logoSize=imageSize/5;
		//int o=(imageSize-logoSize)/2;
		int o=(imageSize-logo.getHeight())/2;
		gs.drawImage(logo, o, o,60,60,null );
		gs.dispose();
		bufferedImage.flush();
		try {
			ImageIO.write(bufferedImage, "png", new File("D:/qrcode.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("产生了问题");
		}
		System.out.println("成功！！");
}

	private static BufferedImage scale(String logoPath, int width, int height, boolean hasFiller) throws Exception {
		// TODO Auto-generated method stub
		double ratio=0.0;
		File file=(new File(logoPath));
		BufferedImage srcImage=ImageIO.read(file);
		Image destImage=srcImage.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
		if((srcImage.getHeight()>height)||(srcImage.getWidth()>width)){
			if(srcImage.getHeight()>srcImage.getWidth()){
				ratio=(new Integer(height).doubleValue()/srcImage.getHeight());
			}
			else{
				ratio=(new Integer(width).doubleValue()/srcImage.getWidth());
				
			}
			AffineTransformOp op=new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio),null);
			destImage=op.filter(srcImage, null);
		}
		if(hasFiller){
			BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
			Graphics2D graphic=image.createGraphics();
			graphic.setColor(Color.white);
			
			graphic.fillRect(0, 0, width, height);
			if(width==destImage.getWidth(null)){
				graphic.drawImage(destImage, 0, (height-destImage.getHeight(null))/2, destImage.getWidth(null), destImage.getHeight(null), Color.white, null);
			}
			else {
				graphic.drawImage(destImage,  (width-destImage.getWidth(null))/2,0, destImage.getWidth(null), destImage.getHeight(null), Color.white, null);
			}
			graphic.dispose();
			destImage=image;
		}
		
		return (BufferedImage) destImage;
	}
}

