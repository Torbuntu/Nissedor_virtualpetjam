class Seed {

	def x, y, speed
	

	void reset(s){
		speed = s.randInt(1,2)
		x = randInt(30, 190)
		y = 0
	}
	
	void update(){
		y+=speed
	}
	
	void render(graphics){
	
	}
}
