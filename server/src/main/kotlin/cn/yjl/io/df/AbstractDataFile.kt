package cn.yjl.io.df

abstract class AbstractDataFile(
    override val group: DataFileGroup,
    override val name: String
) : DataFile {

    abstract fun transformFileName(): String

}