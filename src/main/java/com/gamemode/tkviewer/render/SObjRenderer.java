package com.gamemode.tkviewer.render;

import com.gamemode.tkviewer.file_handlers.DatFileHandler;
import com.gamemode.tkviewer.file_handlers.SObjTblFileHandler;
import com.gamemode.tkviewer.resources.Frame;
import com.gamemode.tkviewer.resources.Resources;
import com.gamemode.tkviewer.resources.SObject;
import com.gamemode.tkviewer.utilities.FileUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class SObjRenderer {

    SObjTblFileHandler tileSObjTbl;
    TileRenderer tileRenderer;

    public SObjRenderer() {
        DatFileHandler tileDat = new DatFileHandler(Resources.NTK_DATA_DIRECTORY + File.separator + "tile.dat");

        this.tileRenderer = new TileRenderer("tilec", "TileC.pal", "TILEC.TBL");
        this.tileSObjTbl = new SObjTblFileHandler(tileDat.getFile("SObj.tbl"));
        System.out.println();
    }

    public SObjRenderer(TileRenderer tileRenderer, SObjTblFileHandler tileSObjTbl) {
        this.tileRenderer = tileRenderer;
        this.tileSObjTbl = tileSObjTbl;
    }

    public BufferedImage renderSObject(int sObjIndex) {
        SObject sObj = this.tileSObjTbl.objects.get(sObjIndex);
        int sObjHeight = sObj.getHeight();

        BufferedImage image = new BufferedImage(Resources.TILE_DIM, sObjHeight * Resources.TILE_DIM, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphicsObject = image.createGraphics();
        for (int i = 0; i < sObjHeight; i++) {
            int tileIndex = sObj.getTileIndices().get(i);

            BufferedImage tile = this.tileRenderer.renderTile(tileIndex);
            Frame frame = FileUtils.getFrameFromEpfs(tileIndex, this.tileRenderer.tileEpfs);

            if (tileIndex > -1) {
                int b = (sObjHeight - i) * Resources.TILE_DIM;
                graphicsObject.drawImage(
                        tile,
                        null,
                        frame.getLeft(),
                        (sObjHeight - i - 1) * Resources.TILE_DIM + frame.getTop());
            }
        }

        return image;
    }

    public void dispose() {
        tileRenderer.dispose();
        tileSObjTbl.close();
    }
}
