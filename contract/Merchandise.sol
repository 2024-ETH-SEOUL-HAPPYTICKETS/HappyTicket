// SPDX-License-Identifier: MIT
pragma solidity >=0.8.2 <0.9.0;

import "@openzeppelin/contracts/token/ERC721/extensions/ERC721Enumerable.sol";
import "@openzeppelin/contracts/access/Ownable.sol";

contract Merchandise is ERC721Enumerable, Ownable {
    string musician;
    string metadataURI;

    // 최초구매자 리스트 (tokenId => bool)
    mapping(uint => bool) internal isFirstOwner;

    constructor(string memory _name, string memory _symbol, string memory _metadataURI) ERC721(_name, _symbol) Ownable(msg.sender) {
        musician = _name;
        metadataURI = _metadataURI;
    }

    // 수집용 굿즈 NFT 발급, admin만 실행 가능
    function mintMerch(address _addr) public onlyOwner {
        uint _tokenId = totalSupply() + 1;
        _mint(_addr, _tokenId);
    }

    // 백에서 input값 받아서 최초구매자 상태 업데이트 기능
    function isFirst(uint _tokenId, bool _isFirst) external onlyOwner {
        isFirstOwner[_tokenId] = _isFirst;
    }

    // 최초 구매자인지 아닌지 구분, 상품성 있는 URI / 상품성 없는 URI
    function tokenURI(uint _tokenId) public view override returns(string memory) {
        if(isFirstOwner[_tokenId]) {
            return string(abi.encodePacked(metadataURI, '/', musician, '_Yes.json'));
        } else {
            return string(abi.encodePacked(metadataURI, '/', musician, '_No.json'));
        }
    }
}