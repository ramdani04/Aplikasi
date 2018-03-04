-- phpMyAdmin SQL Dump
-- version 4.4.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 02 Mar 2018 pada 01.17
-- Versi Server: 5.6.26
-- PHP Version: 5.5.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_pengarsipan`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbl_disposisi`
--

CREATE TABLE IF NOT EXISTS `tbl_disposisi` (
  `no_disposisi` varchar(16) NOT NULL,
  `no_agenda` varchar(15) NOT NULL,
  `no_surat` varchar(30) NOT NULL,
  `jenis_surat` varchar(30) NOT NULL,
  `kepada` varchar(30) NOT NULL,
  `keterangan` varchar(150) NOT NULL,
  `status_surat` varchar(30) NOT NULL,
  `tanggapan` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `tbl_disposisi`
--

INSERT INTO `tbl_disposisi` (`no_disposisi`, `no_agenda`, `no_surat`, `jenis_surat`, `kepada`, `keterangan`, `status_surat`, `tanggapan`) VALUES
('DIS0001', 'AGD0001', 'SRT-20180219-001', 'Resmi', 'PT. Sejahtera', 'Penerimaan Pekerjaan', 'Ada Tindakan', 'Informasi Ini akan saya sampaikan melalui hubin smk 2'),
('DIS0002', 'AGD0002', 'SRT-20180219-012', 'Resmi', 'PT. Ojo Lali', 'Penerimaan pegawai Ojolali', 'Ada Tindakan', 'hubin akan menyampaikan inofrmasi ini'),
('DIS0003', 'AGD0003', 'SRT-20180220-113', 'Resmi', 'PT. ASA', ' Uji Kopetensi Kelas 12', 'Ada Tindakan', 'oke'),
('DIS0004', 'AGD0004', 'SRT-20180220-114', 'Resmi', 'PT. ASA', 'Uji KOM', 'Ada Tindakan', 'iya'),
('DIS0005', 'AGD0004', 'SRT-20180220-114', 'Resmi', 'PT. ASA', 'Uji KOM', 'Ada Tindakan', 'iya');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbl_petugas`
--

CREATE TABLE IF NOT EXISTS `tbl_petugas` (
  `id` varchar(15) NOT NULL,
  `nama_depan` varchar(30) NOT NULL,
  `nama_belakang` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `hak_akses` enum('Admin','Petugas') NOT NULL,
  `status` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `tbl_petugas`
--

INSERT INTO `tbl_petugas` (`id`, `nama_depan`, `nama_belakang`, `password`, `hak_akses`, `status`) VALUES
('PTS0001', 'indra', 'gunawan', '123', 'Admin', 'TIDAK AKTIF'),
('PTS0002', 'Prisma', 'Diana', '2134', 'Petugas', 'TIDAK AKTIF'),
('PTS0003', 'contoh', '1', '123', 'Admin', 'AKTIF');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbl_surat_keluar`
--

CREATE TABLE IF NOT EXISTS `tbl_surat_keluar` (
  `no_agenda` varchar(15) NOT NULL,
  `id` varchar(15) NOT NULL,
  `jenis_surat` varchar(30) NOT NULL,
  `tanggal_kirim` date NOT NULL,
  `no_surat` varchar(30) NOT NULL,
  `pengirim` varchar(30) NOT NULL,
  `balasan` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `tbl_surat_keluar`
--

INSERT INTO `tbl_surat_keluar` (`no_agenda`, `id`, `jenis_surat`, `tanggal_kirim`, `no_surat`, `pengirim`, `balasan`) VALUES
('DIS0001', 'indra', 'Resmi', '2018-02-19', 'SRT-20180219-001', 'PT. Sejahtera', 'Balasan'),
('DIS0002', 'indra', 'Resmi', '2018-02-19', 'SRT-20180219-012', 'PT. Ojo Lali', 'balsan'),
('DIS0003', 'indra', 'Resmi', '2018-02-20', 'SRT-20180220-113', 'PT. ASA', 'Kami Menerima'),
('DIS0004', 'contoh', 'Resmi', '2018-02-21', 'SRT-20180220-114', 'PT. ASA', 'iya'),
('DIS0005', 'contoh', 'Resmi', '2018-02-20', 'SRT-20180220-114', 'PT. ASA', 'iya');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbl_surat_masuk`
--

CREATE TABLE IF NOT EXISTS `tbl_surat_masuk` (
  `no_agenda` varchar(15) NOT NULL,
  `id` varchar(15) NOT NULL,
  `jenis_surat` varchar(30) NOT NULL,
  `tanggal_terima` date NOT NULL,
  `no_surat` varchar(30) NOT NULL,
  `pengirim` varchar(30) NOT NULL,
  `perihal` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `tbl_surat_masuk`
--

INSERT INTO `tbl_surat_masuk` (`no_agenda`, `id`, `jenis_surat`, `tanggal_terima`, `no_surat`, `pengirim`, `perihal`) VALUES
('AGD0001', 'indra', 'Resmi', '2018-02-19', 'SRT-20180219-001', 'PT. Sejahtera', 'Penerimaan Pekerjaan'),
('AGD0002', 'indra', 'Resmi', '2018-02-19', 'SRT-20180219-012', 'PT. Ojo Lali', 'Penerimaan pegawai Ojolali'),
('AGD0003', 'indra', 'Resmi', '2018-02-19', 'SRT-20180220-113', 'PT. ASA', ' Uji Kopetensi Kelas 12'),
('AGD0004', 'indra', 'Resmi', '2018-02-20', 'SRT-20180220-114', 'PT. ASA', 'Uji KOM');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_disposisi`
--
ALTER TABLE `tbl_disposisi`
  ADD PRIMARY KEY (`no_disposisi`);

--
-- Indexes for table `tbl_petugas`
--
ALTER TABLE `tbl_petugas`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_surat_keluar`
--
ALTER TABLE `tbl_surat_keluar`
  ADD PRIMARY KEY (`no_agenda`);

--
-- Indexes for table `tbl_surat_masuk`
--
ALTER TABLE `tbl_surat_masuk`
  ADD PRIMARY KEY (`no_agenda`);

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `tbl_surat_keluar`
--
ALTER TABLE `tbl_surat_keluar`
  ADD CONSTRAINT `tbl_surat_keluar_ibfk_1` FOREIGN KEY (`no_agenda`) REFERENCES `tbl_disposisi` (`no_disposisi`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
