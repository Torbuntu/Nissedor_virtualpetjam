/**
 This is the primary Nisse class to manage all things Nisse.
 */

class Nisse {
	def x, y, offset = 0
	// Default standing position.
	def defaultSprite = 7
	def fore = true
	def sleeping = false

	def move
	def dir = 0
	def recharge = 0
	def sleepTime = 0

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

	def energyLoss = 0

	def generalWear = 20


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

		if(energyLoss > 0){
			energy--
			if (energy < 0) {
				energyLoss = 0
				energy = 0
			}
			energyLoss--
		}

		if(generalWear > 0){
			generalWear--
			if(generalWear <= 0){
				generalWear = 250
				hungry += 5
				bored += 5
				if(bored == 50){
					mood = "Want to play hide and seek?"
					answer = 100
				}
				energyLoss += 2
			}

		}

		if (sleeping) {
			sleepTime--
			if(energy<100)energy++
			if(hungry<80)hungry++
			if (sleepTime < 0) {
				sleeping = false
				energyLoss = -100
			}
			return
		}

		// !fore is hiding! Go click on him
		if (!fore) {
			//wait for click
			return
		}

		if (recharge <= 0 && energy > 0) {
			recharge = 200
			move = system.randInt(5, 45)
			energyLoss += (int) (move / 3)
			hungry -= (int) (energy / 2)
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

	def getRelativeX(){
		x+offset
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
		if (sleeping) {
			graphics.drawString(1, "Zzzz"+('.'*(sleepTime%3)), x+offset, y)
			graphics.sprite(defaultSprite, x + offset, y + 10, 2, 90)
			return
		}
		if (move > 0) graphics.sprite(dir == 0 ? walkRight[animIndex] : walkLeft[animIndex], (x + offset), y, 2)
		//Still
		else graphics.sprite(defaultSprite, (x + offset), y, 2)
	}

	// If Nisse eats, spend 10 energy and reduce 15 hunger points.
	def tryFeed() {
		if (sleeping) return
		if (hungry > 50 && energy > 25) {
			energyLoss += 10
			hungry -= 50
		}
	}

	// Playing makes Nisse hungry by 10 points and costs 25 energy.
	def tryPlay() {
		if (sleeping) return false
		if (hungry < 20 && energy > 30) {
			energyLoss += 25
			hungry += 10
			bored = 0
			return true
		}
		return false
	}

	def hide(s){
		fore = false
		x = s.randInt(15, 205)
	}

	// Check on how Nisse is doing
	def inquire() {
		answer = 100
		if (sleeping) {
			mood = "Zzzz..."
			return
		}
		if (energy < 10) {
			mood = "I'm quite tired..."
			return
		}
		if (hungry > 50) {
			mood = "I could eat..."
			return
		}
		if (bored > 50) {
			mood = "I'm kind of bored here..."
			return
		}
		mood = ":)"
	}

	def trySleep() {
		if (energy < 15) {
			sleeping = true
			sleepTime = (100 - energy)
		}
	}
}
