// SPDX-License-Identifier: MIT
pragma solidity >=0.8.2 <0.9.0;

import "@openzeppelin/contracts/token/ERC721/extensions/ERC721Enumerable.sol";
import "@openzeppelin/contracts/access/Ownable.sol";
import "Rendering.sol";
import "RenderingParams.sol";

contract Ticket is ERC721Enumerable, Ownable {
    // string metadataURI;
    uint public total;
    string public initial;
    string public email;

    function fill(string memory _initial, string memory _email) public {
        initial = _initial;
        email = _email;
    }

    // 콘서트 리스트
    mapping(string => uint) internal concertNum;

    // 입장 이벤트
    event Entered(uint indexed _tokenId);

    // 주최자의 의뢰대로 인풋값 입력, admin = contract 배포자
    constructor(/*string memory _metadataURI*/) ERC721("HappyTicket", "HTK") Ownable(msg.sender) {
        /*metadataURI = _metadataURI;*/
    }

    // 콘서트 정보 등록
    function registerConcert(
        string memory _musician,
        uint _concertNum
    ) external onlyOwner {
        concertNum[_musician] = _concertNum;
    }

    // 티켓 발급
    function mintTicket() external onlyOwner {
        total++;
        uint tokenId = total;
        _mint(msg.sender, tokenId);
    }

    // 입장
    function enter(uint _tokenId, uint _time) external onlyOwner {
        require(block.timestamp < _time, "Time over to enter");
        require(_time < block.timestamp + 30 minutes, "Not yet to enter");
        emit Entered(_tokenId);
        _burn(_tokenId);
    }

    // Implement tokenURI function using NFTRenderer library to generate JSON metadata
    function tokenURI(uint _tokenId) public view override returns (string memory) {
        RenderParams memory params = RenderParams({
            musician: "BTS",
            title: "PERMISSION TO DANCE ON STAGE",
            initial: initial,
            email: email,
            location: "Seoul Olympic Main Stadium",
            date: "Sunday, May 12, 2024"
        });

        return NFTRenderer.render(params);
    }
}
