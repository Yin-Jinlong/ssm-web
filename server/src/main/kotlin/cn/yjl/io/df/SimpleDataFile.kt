package cn.yjl.io.df

/**
 *
 * @author YJL
 */
class SimpleDataFile internal constructor(
    group: DataFileGroup,
    name: String
) : AbstractDataFile(group, name) {

    override fun transformFileName(): String = name

}