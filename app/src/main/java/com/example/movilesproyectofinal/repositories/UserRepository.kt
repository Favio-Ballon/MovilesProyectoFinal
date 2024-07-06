
import android.content.Context
import com.example.movilesproyectofinal.api.APIProyecto
import com.example.movilesproyectofinal.models.dto.LoginRequestDTO
import com.example.movilesproyectofinal.models.dto.LoginResponseDTO
import com.example.movilesproyectofinal.models.dto.RegisterRequestDTO
import com.example.movilesproyectofinal.repositories.PreferencesRepository
import com.example.movilesproyectofinal.repositories.RetrofitRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UserRepository {
    fun doLogin(
        email: String,
        password: String,
        success: (LoginResponseDTO?) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APIProyecto =
            retrofit.create(APIProyecto::class.java)
        service.login(LoginRequestDTO(email, password))
            .enqueue(object : Callback<LoginResponseDTO> {
                override fun onResponse(
                    res: Call<LoginResponseDTO>,
                    response: Response<LoginResponseDTO>
                ) {
                    if (response.code() == 401) {
                        success(null)
                        return
                    }
                    val loginResponse = response.body()
                    success(loginResponse)
                }

                override fun onFailure(res: Call<LoginResponseDTO>, t: Throwable) {
                    failure(t)
                }
            })
    }

    fun doRegister(
        user: String,
        email: String,
        password: String,
        telefono: String,
        success: (LoginResponseDTO?) -> Unit,
        failure: (Throwable) -> Unit
    ){
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APIProyecto =
            retrofit.create(APIProyecto::class.java)

        service.register(RegisterRequestDTO(user, email, password, telefono))
            .enqueue(object : Callback<LoginResponseDTO> {
                override fun onResponse(
                    res: Call<LoginResponseDTO>,
                    response: Response<LoginResponseDTO>
                ) {
                    if (response.code() == 401) {
                        success(null)
                        return
                    }
                    val loginResponse = response.body()
                    success(loginResponse)
                }

                override fun onFailure(res: Call<LoginResponseDTO>, t: Throwable) {
                    failure(t)
                }
            })
    }

    fun doLogout(context: Context) {
        PreferencesRepository.saveToken("", context)
    }
}