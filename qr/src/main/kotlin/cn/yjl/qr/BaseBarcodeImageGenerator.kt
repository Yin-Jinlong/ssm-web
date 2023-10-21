package cn.yjl.qr

import cn.yjl.qr.drawer.Drawer

/**
 *
 * @author YJL
 */
open class BaseBarcodeImageGenerator<D : Drawer>(
    drawer: D
) : AbstractBarcodeImageGenerator<D>(drawer)