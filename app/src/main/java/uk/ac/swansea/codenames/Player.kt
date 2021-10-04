package uk.ac.swansea.codenames

class Player {
    var nickname: String
    var team: String? = null
    var isHost = false
    var isSpymaster = false

    constructor(nickname: String) {
        this.nickname = nickname
    }

    constructor(nickname: String, team: String?) {
        this.nickname = nickname
        this.team = team
    }
}