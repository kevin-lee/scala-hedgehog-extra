import wartremover.WartRemover.autoImport.Warts

object SbtProjectInfo {
  final case class ProjectName(projectName: String) extends AnyVal

  def commonWarts(scalaBinaryVersion: String): Seq[wartremover.Wart] =
    scalaBinaryVersion match {
      case "2.10" =>
        Seq.empty
      case _ =>
        Warts.all
    }

}
