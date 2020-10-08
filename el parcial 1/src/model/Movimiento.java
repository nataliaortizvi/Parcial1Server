package model;

public class Movimiento {
	 //metadata
    private String type;
    private float x,y;

    public Movimiento(){
    }

    public Movimiento(float x, float y, String type){
        super();
        this.x = x;
        this.y = y;
        this.type = type;
    }
   
	

	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
