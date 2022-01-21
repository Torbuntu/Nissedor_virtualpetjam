class Nissedor extends leikr.Engine {

	def background = "parallax-forest-back-trees.png"
	def middleGround = "parallax-forest-middle-trees.png"
	def foreground = "parallax-forest-front-trees.png"
	def lights = "parallax-forest-lights.png"

	def cursorUp = "cursor_pointer3D_shadow.png"

	def hud = "hud.png"

	def happiness = "happiness.png"
	def boredom = "boredom.png"

	def action, debug = false

	def click = 0
	def clickPos = [x: 0, y: 0]

	def mapOffset = -5

	def hideCount = 0
	def hideTimer = 10

	Nisse nisse

	void create() {
		nisse = new Nisse()
	}

	void update(float delta) {
		action = key("Space")

		// update click action
		if (mouseClick()) {
			clickPos.x = (Integer) mouseX()
			clickPos.y = (Integer) mouseY()
			click = 10

			if (mouseX() > nisse.x - 5 && mouseX() < nisse.x + 25) {
				if (mouseY() > nisse.y) {
					if (nisse.fore) {
						hideCount = 10
						nisse.fore = false
						nisse.x = randInt(15, 205)
					} else {
						nisse.happiness += 20
						if (nisse.happiness > 100) nisse.happiness = 100
						nisse.fore = true
					}
				}
			}
		}

		if (mouseX() > 220 && mapOffset > -30) mapOffset--
		if (mouseX() < 20 && mapOffset < 0) mapOffset++


		// Update Nisse
		nisse.update(mapOffset, getSystem())


		if (keyPress("T")) nisse.fore = !nisse.fore
		if (keyPress("D")) debug = !debug
	}


	void render() {

		if (hideCount > 0) {
			renderHud()
			hiding()
			return
		}

		// BEGIN background render
		drawTexture(background, mapOffset, 0)

		if (action) {
			drawTexture(lights, mapOffset, 0)
		}

		drawTexture(middleGround, mapOffset / 1.5, 0)

		if (!nisse.checkFore()) nisse.render(getGraphics())
		drawTexture(foreground, mapOffset / 2, 0)
		if (nisse.checkFore()) nisse.render(getGraphics())
		// END background render

		// BEGIN Nisse
		//nisse.render(getGraphics())

		// END Nisse

		renderHud()

		// DEBUG
		if (debug) renderDebug()

	}


	void renderDebug() {
		drawString(1, "map offset: $mapOffset", 0, 0)
		drawString(1, "Fore: ${nisse.fore}", 0, 10)
	}

	void renderHud() {
		// render UI
		drawTexture(hud, 0, 0)
		// BEGIN Cursor
		drawTexture(cursorUp, mouseX(), mouseY(), 10, 12)

		// Click action
		if (click > 0) {
			click--
			drawCircle(15, clickPos.x, clickPos.y, click + 5)
		}

		drawTexture(happiness, 4, 8, (82 / nisse.happiness) * 100, 11)
		drawTexture(boredom, 236 - nisse.boredom, 8, nisse.boredom, 11)

		if (mouseY() < 20) {
			if (mouseX() < 90) {
				drawString(1, "Happiness", mouseX() - 6, mouseY() - 6)
			}
			if (mouseX() > 150) {
				drawString(1, "Boredom", mouseX() - 20, mouseY() - 6)
			}
		}
	}

	void hiding() {
		drawString(1, "$hideCount", 110, 5)
		hideTimer--
		if (hideTimer < 0) {
			hideTimer = 10
			hideCount--
		}

	}
}

// TODO
/*

	
	Actions:
	- Offer food to Nisse (improves mood if accepted) not hungry? Try playing a game.
	- Play games with Nisse (hide and seek. Catch (Nisse throws something breakable up, you catch before it lands)
	
	Goals?:
	- Nisse stays happy and doesn't kill/break stuff
	- Nisse grows up and eventually enters Nissedor when fully satisfied and Nissedor appears




*/


