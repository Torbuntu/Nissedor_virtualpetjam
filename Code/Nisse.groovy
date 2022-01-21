/**
 This is the primary Nisse class to manage all things Nisse.
 */

class Nisse {
	def x, y, offset
	// Default standing position.
	def defaultSprite = 7
	def fore = true

	def move
	def dir = 0
	def recharge = 0

	def energy = 100
	def happy = 100
	def bored = 0
	def hungry = 0
	def mood = "Nisse's mood here"
	def answer = 0

	def animIndex = 0
	def animSpeed = 10
	def walkRight = [3, 4, 5]
	def walkLeft = [9, 10, 11]


	// Look to see if the Nisse is in the foreground or background
	boolean checkFore() {
		fore
	}

	Nisse() {
		x = 100
		y = 110
		move = 0
	}

	void update(offset, system) {
		if (fore) this.offset = offset / 2
		else this.offset = offset / 1.5

		// !fore is hiding! Go click on him
		if (!fore) {
			//wait for click
			return
		}

		if (recharge <= 0 && energy > 0) {
			recharge = 200
			move = system.randInt(5, 45)
			energy -= (int)(move/3)
			if(energy < 0) energy = 0
		} else {
			recharge--
		}

		updateAnim()
		if (move > 0) {
			move--
			if (dir == 0) {
				x++
				if (x > 200) dir = 1
			}
			if (dir == 1) {
				x--
				if (x < 20) dir = 0
			}

		}
	}

	def updateAnim() {
		animSpeed--
		if (animSpeed <= 0) {
			animSpeed = 10
			if (animIndex < 2) animIndex++
			else animIndex = 0
		}
	}

	def render(graphics) {
		if (move > 0) graphics.sprite(dir == 0 ? walkRight[animIndex] : walkLeft[animIndex], x + offset, y, 2)
		//Still
		else graphics.sprite(defaultSprite, x + offset, y, 2)
	}

	// If Nisse eats, spend 10 energy and reduce 15 hunger points.
	def tryFeed(){
		if(hungry > 50 && energy > 25){
			energy -= 10
			hungry -= 15
		}
	}

	// Playing makes Nisse hungry by 10 points and costs 25 energy.
	def tryPlay(){
		if(hungry < 20 && energy > 40){
			energy -= 25
			hungry += 10
		}
	}

	// Check on how Nisse is doing
	def inquire(){
		answer = 100
		if(energy < 10) {
			mood = "I'm quite tired..."
			return
		}
		if(hungry > 50){
			mood = "I could eat..."
			return
		}
		if(bored > 50){
			mood = "I'm kind of bored here..."
			return
		}
		mood = ":)"
	}

	def trySleep(){

	}
}
