package com.danieldisu.hnnotify.navigation


enum class NavigationRouteAddress(val value: String) {
    TopStories("/topstories"),
    Interests("/interests"),
    AddInterest("/interests/add"),
    EditInterest("/interests/edit/{interestId}"),
}

sealed class NavigationRoute(
    private val routeAddress: NavigationRouteAddress,
) {

    abstract fun getRouteValue(): String

    object TopStories : ArgumentLessNavigationRoute(NavigationRouteAddress.TopStories)

    object Interests : ArgumentLessNavigationRoute(NavigationRouteAddress.Interests)

    object AddInterest : ArgumentLessNavigationRoute(NavigationRouteAddress.AddInterest)

    data class EditInterest(val interestId: String) : SingleArgumentNavigationRoute(
        routeAddress = NavigationRouteAddress.EditInterest,
        argument1Key = "interestId",
        argument1Value = interestId,
    )
}


sealed class ArgumentLessNavigationRoute(
    private val routeAddress: NavigationRouteAddress
) : NavigationRoute(routeAddress) {
    override fun getRouteValue() = routeAddress.value
}

sealed class SingleArgumentNavigationRoute(
    private val routeAddress: NavigationRouteAddress,
    private val argument1Key: String,
    private val argument1Value: String,
) : NavigationRoute(routeAddress) {
    override fun getRouteValue() = routeAddress.value.replace("{$argument1Key}", argument1Value)
}
