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

	def happy = 100
	def tired = 1
	def bored = 0
	def hungry = 0

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

		if (recharge <= 0) {
			recharge = 200
			move = system.randInt(5, 45)
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

		if (boredom < 82) boredom++
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
}
