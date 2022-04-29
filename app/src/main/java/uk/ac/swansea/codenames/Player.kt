package uk.ac.swansea.codenames

/**
 * A player object, used to tell what team they are and if they are spymaster/host.
 */
class Player {
    var nickname: String
    var team: String? = null
    var isHost = false
    var isSpymaster = false

    /**
     * Constructs object with just nickname.
     */
    constructor(nickname: String) {
        this.nickname = nickname
    }

    /**
     * Constructs object with nickname and team.
     */
    constructor(nickname: String, team: String?) {
        this.nickname = nickname
        this.team = team
    }
}