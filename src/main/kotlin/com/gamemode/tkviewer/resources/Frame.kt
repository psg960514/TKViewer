package com.gamemode.tkviewer.resources

import java.nio.ByteBuffer

class Frame(val top: Int, val left: Int, val bottom: Int, val right: Int, val width: Int, val height: Int,
            val pixelDataOffset: Long, val stencilDataOffset: Long, val rawPixelData: ByteBuffer,
            val rawStencilData: ByteBuffer, val stencil: Stencil)