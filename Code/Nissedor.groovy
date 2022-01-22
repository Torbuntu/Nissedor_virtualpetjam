class Nissedor extends leikr.Engine {

	// Images
	def background = "parallax-forest-back-trees.png"
	def middleGround = "parallax-forest-middle-trees.png"
	def foreground = "parallax-forest-front-trees.png"
	def lights = "parallax-forest-lights.png"
	def cursorUp = "cursor_pointer3D_shadow.png"
	def hud = "hud.png"
	def title = "title.png"
	def begin = false

	def action, debug = false

	def click = 0
	def clickPos = [x: 0, y: 0]

	def mapOffset = -5

	def hideCount = 0
	def hideTimer = 10

	def found = 0

	Nisse nisse


	void create() {
		nisse = new Nisse()
	}

	void update(float delta) {
		action = key("Space")
		if(!begin){
			if(key("Space") || key("X") || key("Enter") || mouseClick()){
				begin = true
			}
			return
		}

		// update click action
		if (mouseClick()) {
			clickPos.x = (Integer) mouseX()
			clickPos.y = (Integer) mouseY()
			click = 10

			// Feed
			if(checkIn([11, 26, 36, 50])){
				nisse.tryFeed()
			}
			// Play
			if(checkIn([28, 42, 36, 50])){
				if(nisse.tryPlay()){
					hideCount = 10
					nisse.hide(getSystem())
				}
			}
			// Inquire
			if(checkIn([46, 60, 36, 50])){
				nisse.inquire()
			}
			// Sleep
			if(checkIn([62, 78, 36, 50])){
				nisse.trySleep()
			}

			// Click Nisse
			if (checkIn([nisse.getRelativeX()+8, nisse.getRelativeX()+22, nisse.y+10, nisse.y+32])) {
				if (nisse.fore) {
					hideCount = 10
					nisse.hide(getSystem())
				} else {
					nisse.happy += 20
					if (nisse.happy > 100) nisse.happy = 100
					nisse.fore = true
					found = 30
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

		if(!begin){
			drawTexture(background, mapOffset, 0)
			drawTexture(lights, mapOffset, 0)
			drawTexture(title, 0,0)
			return
		}

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

		if(found > 0){
			found--
			drawString(1, "!!", nisse.getRelativeX()+10, nisse.y)
		}

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
		drawCircle(10, mouseX(), mouseY(), 3)
	}

	void renderHud() {
		// render UI
		drawTexture(hud, 0, 0)
		fillRect(15, 4, 8, (nisse.energy), 11)
		// BEGIN Cursor
		drawTexture(cursorUp, mouseX(), mouseY(), 10, 12)

		if(nisse.answer > 0){
			nisse.answer--
			drawString(1, nisse.mood, nisse.getRelativeX(), nisse.y)
		}

		// Click action
		if (click > 0) {
			click--
			drawCircle(15, clickPos.x, clickPos.y, click + 5)
		}

		if (mouseY() < 20) {
			if (mouseX() < 90) {
				drawString(1, "Energy", mouseX() - 6, mouseY() - 6)
			}
		}
		if(checkIn([11, 26, 36, 50])){
			drawString(1, "Feed", mouseX()-5, mouseY()-8)
		}
		if(checkIn([28, 42, 36, 50])){
			drawString(1, "Play", mouseX()-5, mouseY()-8)
		}
		if(checkIn([46, 60, 36, 50])){
			drawString(1, "Inquire", mouseX()-5, mouseY()-8)
		}
		if(checkIn([62, 78, 36, 50])){
			drawString(1, "Sleep", mouseX()-5, mouseY()-8)
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

	boolean checkIn(box){
		mouseX() > box[0] && mouseX() < box[1] && mouseY() > box[2] && mouseY() < box[3]
	}
}

// TODO
/*

Add some music and sfx

*/


