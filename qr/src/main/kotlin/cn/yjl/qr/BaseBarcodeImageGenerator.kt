package cn.yjl.qr

import cn.yjl.qr.drawer.Drawer

/**
 *
 * @author YJL
 */
class BaseBarcodeImageGenerator<D : Drawer>(
    drawer: D
) : AbstractBarcodeImageGenerator<D>(drawer)