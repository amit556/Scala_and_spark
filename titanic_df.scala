import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import org.apache.spark.sql._

object TitanicAnalysis {

  val conf = new SparkConf().setAppName("TitanicAnalysis").setMaster("local")
  val sc = new SparkContext(conf)
  val sqlContext = new SQLContext(sc)

  def main(args: Array[String]) {
    import sqlContext.implicits._
    

    val data = sc.textFile("titanic.txt").map(_.split("\t"))
      .filter(lines => lines(0) != "Name")
      .map(tdata => Titanic(tdata(0), tdata(1), tdata(2), tdata(3), tdata(4)))
      .toDF()

    data.registerTempTable("TitanicData")

    //please un-comment the section one by one to see results
    
    //Count of Male/Female on Titanic
    
    
    val countOfMaleFemale = sqlContext.sql("select Sex,count(*) as TotalCount from TitanicData group by Sex")
    countOfMaleFemale.show()
    
    val countOfMalesurvived = sqlContext.sql("select count(*) as No_Of_Male_Survived from TitanicData where (Sex = 'male' and Survived = 1 )")
    countOfMalesurvived.show()
    
    val countOfFemalesurvived = sqlContext.sql("select count(*) as No_Of_Female_Survived from TitanicData where (Sex = 'female' and Survived = 1 )")
    countOfFemalesurvived.show()
    
    val agewisesurvived = sqlContext.sql("select Age,count(*) as agewisesurvived from TitanicData where ( Survived = 1 ) group by Age order by agewisesurvived desc limit 1 ")
    agewisesurvived.show()
  }
  }
case class Titanic(Name: String, PClass: String, Age: String, Sex: String, Survived: String)