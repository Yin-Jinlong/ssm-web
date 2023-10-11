package cn.yjl.util

import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Paint
import org.jetbrains.skia.Rect

fun Canvas.drawRect(
    l: Float,
    t: Float,
    w: Float,
    h: Float,
    paint: Paint
) = drawRect(Rect(l, t, l + w, t + h), paint)

fun Canvas.drawOval(
    l: Float,
    t: Float,
    w: Float,
    h: Float,
    paint: Paint
) = drawOval(Rect(l, t, l + w, t + h), paint)

fun Canvas.drawArc(
    l: Float,
    t: Float,
    w: Float,
    h: Float,
    s: Float,
    sweep: Float,
    paint: Paint
) = drawArc(l, t, l + w, t + h, s, sweep, true, paint)
